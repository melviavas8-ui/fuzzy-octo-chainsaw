package com.darkmagician6.eventapi;

import com.darkmagician6.eventapi.events.Event;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Minimal reflection based event bus compatible with the EventAPI annotations used by the Zenith core.
 * Handlers are the declared methods of the registered object that carry {@link EventTarget} and take one
 * {@link Event} parameter; dispatch matches the exact event class and runs handlers by ascending priority.
 */
public final class EventManager {
   private static final Map<Class<? extends Event>, List<Handler>> HANDLERS = new ConcurrentHashMap<>();

   private EventManager() {
   }

   @SuppressWarnings("unchecked")
   public static void register(Object source) {
      for (Method m : source.getClass().getDeclaredMethods()) {
         if (!valid(m)) continue;
         m.setAccessible(true);
         Class<? extends Event> type = (Class<? extends Event>) m.getParameterTypes()[0];
         List<Handler> list = HANDLERS.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>());
         boolean dup = false;
         for (Handler h : list) if (h.source == source && h.method.equals(m)) { dup = true; break; }
         if (dup) continue;
         list.add(new Handler(source, m, m.getAnnotation(EventTarget.class).value()));
         List<Handler> sorted = new ArrayList<>(list);
         sorted.sort(Comparator.comparingInt(h -> h.priority));
         list.clear();
         list.addAll(sorted);
      }
   }

   public static void unregister(Object source) {
      for (List<Handler> list : HANDLERS.values()) list.removeIf(h -> h.source == source);
   }

   public static <T extends Event> T call(T event) {
      List<Handler> list = HANDLERS.get(event.getClass());
      if (list == null) return event;
      for (Handler h : list) {
         try {
            h.method.invoke(h.source, event);
         } catch (Throwable t) {
            Throwable c = t.getCause() != null ? t.getCause() : t;
            System.err.println("[daamky] event handler " + h.source.getClass().getSimpleName() + "#" + h.method.getName() + " failed: " + c);
            c.printStackTrace();
         }
      }
      return event;
   }

   private static boolean valid(Method m) {
      return m.getParameterCount() == 1 && m.isAnnotationPresent(EventTarget.class) && Event.class.isAssignableFrom(m.getParameterTypes()[0]);
   }

   private static final class Handler {
      final Object source;
      final Method method;
      final byte priority;

      Handler(Object source, Method method, byte priority) {
         this.source = source;
         this.method = method;
         this.priority = priority;
      }
   }
}
