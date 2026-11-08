Шрифты Celestial (из Celestial-Sources/.../celestial/fonts):
  Montserrat-Regular.ttf, Montserrat-Bold.ttf - один и тот же статический Montserrat Bold (700) из оригинала,
      лежит под обоими именами, чтобы Fonts.regular()/bold() нашли начертания без имитации жирности.
  Pixel.ttf - Bit Cell (пиксельный), доступен через Fonts.get("pixel").
Всё запекается автоматически при ./gradlew build (MsdfBaker). Вариативные шрифты не использовать.
