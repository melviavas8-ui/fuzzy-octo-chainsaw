package daamky.modules.combat;

import java.util.Comparator;
import daamky.core.Module;
import daamky.gui.Modules;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Box;
import net.minecraft.world.RaycastContext;
import daamky.core.Rot;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;







public class AttackAura extends Module {
    private static AttackAura inst;

    private final Modules.Mode sort, rotMode, sprintBypass, moveMode;
    private final Modules.Slider reach, aimExtra;
    private final Modules.Bool onlyCrits, walls, breakShield, hitShield, stopUse, stopContainer, onlySpace, aimBow;
    private final Modules.Bool tPlayers, tMobs, tAnimals, tInvis, tNaked;
    private final Modules.Bool visEsp, visHud;

    private LivingEntity target;
    private float spookyProg;
    private boolean wantAttack;
    private int savedSlot = -1;

    public AttackAura() {
        super("Attack Aura", "Автоматически бьет окружающих вас\nсуществ", COMBAT);
        sort = mode("Сортировка", 0, "Умная", "Поле зрения", "Дистанция", "Здоровье");
        rotMode = mode("Режим", 0, "Funtime", "Matrix", "Snap", "SpookyTime");
        sprintBypass = mode("Обход спринта", 0, "Funtime", "Legit", "Snap", "SpookyTime");
        moveMode = mode("Режим", 0, "Незаметный", "Строгий", "Таргет", "Свободный");
        aimBow = bool("Таргетить если у цели лук, арбалет или трезубец", false);
        reach = slider("Дистанция удара", 3.0f, 1.0f, 6.0f, 0.1f, "");
        aimExtra = slider("Дистанция наводки", 0.0f, 0.0f, 32.0f, 0.5f, "");
        onlyCrits = bool("Только криты", true);
        walls = bool("Бить через стены", true);
        breakShield = bool("Ломать щит", true);
        hitShield = bool("Бить через щит", true);
        stopUse = bool("Останавливать при использовании предмета", false);
        stopContainer = bool("Останавливать при открытом контейнере", false);
        onlySpace = bool("Только с пробелом", false);

        tPlayers = new Modules.Bool("Игроки", true);
        tNaked = new Modules.Bool("Голые", true);
        tMobs = new Modules.Bool("Мобы", true);
        tAnimals = new Modules.Bool("Животные", false);
        tInvis = new Modules.Bool("Невидимые", true);
        visHud = new Modules.Bool("Таргет-HUD", true);
        visEsp = new Modules.Bool("ESP", true);
        addTo("Цели", tPlayers, tNaked, tMobs, tAnimals, tInvis);
        addTo("Визуалы", visHud, visEsp);
        inst = this;
        ClientTickEvents.END_CLIENT_TICK.register(c -> { if (enabled) postTick(); });
    }


    private void addTo(String group, Modules.Setting... s) {
        for (Modules.Setting x : settings)
            if (x instanceof Modules.Group g && g.name.equals(group)) { for (Modules.Setting k : s) g.children.add(k); return; }
        for (Modules.Setting k : s) settings.add(k);
    }

    public static LivingEntity target() { return inst != null && inst.on() ? inst.target : null; }
    public static boolean espOn() { return inst != null && inst.on() && inst.visEsp.value; }

    @Override protected void onDisable() { target = null; wantAttack = false; Rot.clear(); restoreSlot(); }

    private void restoreSlot() {
        if (savedSlot >= 0 && mc.player != null) mc.player.getInventory().setSelectedSlot(savedSlot);
        savedSlot = -1;
    }


    private boolean valid(Entity e, double range) {
        if (!(e instanceof LivingEntity le) || e == mc.player || !le.isAlive() || le.getHealth() <= 0) return false;
        if (mc.player.squaredDistanceTo(le) > range * range) return false;
        if (le.isInvisible() && !tInvis.value) return false;
        if (le instanceof PlayerEntity pe) {
            if (!tPlayers.value) return false;
            if (!tNaked.value && pe.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST).isEmpty()
                    && pe.getEquippedStack(net.minecraft.entity.EquipmentSlot.LEGS).isEmpty()) return false;
            return true;
        }
        if (le instanceof AnimalEntity) return tAnimals.value;
        if (le instanceof MobEntity) return tMobs.value;
        return false;
    }


    private float[] rotsTo(Entity e) {
        Vec3d eye = mc.player.getEyePos();
        Box b = e.getBoundingBox();
        double dx = (b.minX + b.maxX) / 2.0 - eye.x, dy = (b.minY + b.maxY) / 2.0 - eye.y, dz = (b.minZ + b.maxZ) / 2.0 - eye.z;
        double h = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, h));
        return new float[]{yaw, MathHelper.clamp(pitch, -90f, 90f)};
    }

    private float angleTo(Entity e) {
        float[] r = rotsTo(e);
        return Math.abs(MathHelper.wrapDegrees(r[0] - mc.player.getYaw())) + Math.abs(r[1] - mc.player.getPitch());
    }

    private LivingEntity pick() {
        double range = reach.value + aimExtra.value;
        Comparator<LivingEntity> cmp = switch (sort.index) {
            case 1 -> Comparator.comparingDouble(this::angleTo);
            case 2 -> Comparator.comparingDouble(e -> mc.player.squaredDistanceTo(e));
            case 3 -> Comparator.comparingDouble(LivingEntity::getHealth);
            default -> Comparator.comparingDouble(e -> mc.player.squaredDistanceTo(e) * 0.6 + e.getHealth() * 0.8 + angleTo(e) * 0.05);
        };
        LivingEntity best = null;
        for (Entity e : mc.world.getEntities()) {
            if (!valid(e, range)) continue;
            if (!walls.value && !mc.player.canSee(e)) continue;
            LivingEntity le = (LivingEntity) e;
            if (best == null || cmp.compare(le, best) < 0) best = le;
        }
        return best;
    }



    private float[] bestAim(LivingEntity t, float curYaw, float curPitch) {
        Box b = t.getBoundingBox();
        double in = 0.08;
        Vec3d eye = mc.player.getEyePos();
        float[] best = null; double bestD = Double.MAX_VALUE;
        for (int iy = 0; iy <= 12; iy++) {
            double y = b.minY + in + (b.maxY - b.minY - 2 * in) * iy / 12.0;
            for (int k = 0; k <= 8; k++) {
                double f = k / 8.0;
                double[][] pts = {
                    {b.minX + in + (b.maxX - b.minX - 2 * in) * f, b.minZ + in}, {b.minX + in + (b.maxX - b.minX - 2 * in) * f, b.maxZ - in},
                    {b.minX + in, b.minZ + in + (b.maxZ - b.minZ - 2 * in) * f}, {b.maxX - in, b.minZ + in + (b.maxZ - b.minZ - 2 * in) * f}};
                for (double[] p : pts) {
                    double dx = p[0] - eye.x, dy = y - eye.y, dz = p[1] - eye.z, h = Math.sqrt(dx * dx + dz * dz);
                    float yw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0), pt = (float) -Math.toDegrees(Math.atan2(dy, h));
                    double dist = Math.abs(MathHelper.wrapDegrees(yw - curYaw)) + Math.abs(pt - curPitch);
                    if (dist < bestD) { bestD = dist; best = new float[]{yw, pt}; }
                }
            }
        }
        return best != null ? best : rotsTo(t);
    }

    private static float step(float cur, float want, float max) {
        float d = MathHelper.wrapDegrees(want - cur);
        if (max < 0) return cur + d;
        return cur + MathHelper.clamp(d, -max, max);
    }

    private boolean hits(LivingEntity t, float yaw, float pitch) {
        Vec3d eye = mc.player.getEyePos();
        double yr = Math.toRadians(yaw), pr = Math.toRadians(pitch);
        Vec3d dir = new Vec3d(-Math.sin(yr) * Math.cos(pr), -Math.sin(pr), Math.cos(yr) * Math.cos(pr));
        Vec3d end = eye.add(dir.multiply(reach.value));
        var hit = t.getBoundingBox().expand(0.0).raycast(eye, end);
        if (hit.isEmpty()) return false;
        if (walls.value) return true;
        var bh = mc.world.raycast(new RaycastContext(eye, hit.get(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player));
        return bh.getType() == net.minecraft.util.hit.HitResult.Type.MISS;
    }


    private void computeRotation(LivingEntity t, boolean attackNow) {
        var p = mc.player;
        float curY = Rot.active ? Rot.yaw : p.getYaw(), curP = Rot.active ? Rot.pitch : p.getPitch();
        float[] aim = bestAim(t, curY, curP);
        int tick = p.age;
        float ny, np;
        switch (rotMode.index) {
            case 2 -> {
                if (!attackNow) { Rot.clear(); return; }
                ny = aim[0]; np = aim[1];
            }
            case 1 -> {
                float ty = aim[0] + (float) (Math.sin(tick) * 3.0) + rnd(0.5f), tp = aim[1] + (float) (Math.cos(tick) * 0.5) + rnd(0.5f);
                ny = step(curY, ty, 52f);
                np = step(curP, tp, hits(t, curY, curP) ? 5f : -1f);
            }
            case 3 -> {
                spookyProg = Math.min(1f, spookyProg + 0.12f + (float) Math.random() / 20f);
                float k = (float) Math.sin(spookyProg * Math.PI / 2.0);
                ny = curY + MathHelper.wrapDegrees(aim[0] - curY) * k; np = curP + (aim[1] - curP) * k;
            }
            default -> {
                ny = step(curY, aim[0], 50f); np = step(curP, aim[1], 10f);
            }
        }

        double f = mc.options.getMouseSensitivity().getValue() * 0.6 + 0.2, gcd = f * f * f * 8.0 * 0.15;
        ny = curY + Math.round((ny - curY) / gcd) * (float) gcd; np = curP + Math.round((np - curP) / gcd) * (float) gcd;
        Rot.set(ny, MathHelper.clamp(np, -90f, 90f));
    }

    private static float rnd(float a) { return (float) ((Math.random() * 2 - 1) * a); }


    private boolean critReady() {
        var p = mc.player;
        if (onlySpace.on() && !mc.options.jumpKey.isPressed()) return true;
        return !p.isOnGround() && p.fallDistance > 0.0 && !p.isClimbing() && !p.isTouchingWater() && !p.hasStatusEffect(StatusEffects.BLINDNESS) && !p.hasVehicle();
    }


    private boolean resetSprint() { return sprintBypass.index != 1 || rotMode.index == 3; }

    private void doAttack(LivingEntity t) {
        var p = mc.player;
        boolean sprint = p.isSprinting() && resetSprint();
        if (sprint) {
            p.networkHandler.sendPacket(new ClientCommandC2SPacket(p, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            p.setSprinting(false);
        }
        mc.interactionManager.attackEntity(p, t);
        p.swingHand(Hand.MAIN_HAND);
        if (sprint) {
            p.networkHandler.sendPacket(new ClientCommandC2SPacket(p, ClientCommandC2SPacket.Mode.START_SPRINTING));
            p.setSprinting(true);
        }
    }

    private void shieldSwap(LivingEntity t) {
        if (!breakShield.value || !(t instanceof PlayerEntity pe) || !pe.isBlocking()) { restoreSlot(); return; }
        var inv = mc.player.getInventory();
        for (int i = 0; i < 9; i++)
            if (inv.getStack(i).getItem() instanceof AxeItem) {
                if (savedSlot < 0) savedSlot = inv.getSelectedSlot();
                inv.setSelectedSlot(i);
                return;
            }
    }


    @Override
    protected void onTick() {
        var p = mc.player;
        wantAttack = false;
        if ((stopUse.value && p.isUsingItem()) || (stopContainer.value && mc.currentScreen instanceof HandledScreen<?>)) { target = null; Rot.clear(); return; }

        LivingEntity best = pick();
        if (target == null || !valid(target, reach.value + aimExtra.value) || (!walls.value && !p.canSee(target)) || sort.index != 0) target = best;
        if (target == null) { Rot.clear(); spookyProg = 0; restoreSlot(); return; }


        Rot.moveFix = moveMode.index != 3;

        double d = Math.sqrt(p.squaredDistanceTo(target));
        boolean cd = p.getAttackCooldownProgress(0.5f) >= 0.93f;
        boolean crit = !onlyCrits.value || critReady();
        boolean shieldOk = hitShield.value || !(target instanceof PlayerEntity pe && pe.isBlocking());
        boolean ready = d <= reach.value && cd && crit && shieldOk;

        computeRotation(target, ready);
        shieldSwap(target);
        wantAttack = ready;
    }


    private void postTick() {
        var p = mc.player;
        if (p == null || mc.world == null || target == null || !wantAttack) return;
        if (!Rot.active) return;
        if (!hits(target, Rot.yaw, Rot.pitch)) return;
        doAttack(target);
        wantAttack = false;
        if (rotMode.index == 2) Rot.clear();
    }
}
