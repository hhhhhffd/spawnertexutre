# SpawnerTexture

Fabric клиент-мод для Minecraft 1.21.4. Заменяет иконку спавнера в инвентаре на текстуру моба, который в нём настроен.

## Как это работает

Мод перехватывает рендер слотов инвентаря через миксин (`HandledScreen.drawSlot`). Если предмет — спавнер с известным мобом, вместо стандартной иконки рисуется PNG-текстура этого моба.

## Добавление текстур

Положи PNG-файлы (16×16) по пути:

```
src/main/resources/assets/spawnertexture/textures/mob/<mob_id>.png
```

Например: `zombie.png`, `skeleton.png`, `creeper.png` и т.д.

Полный список поддерживаемых мобов — в `SpawnerTextureManager.java` (поле `KNOWN_MOBS`).  
Текстуры можно также поставлять отдельным ресурспаком — мод подхватит их автоматически при загрузке/смене пака.

## Сборка

```bash
./gradlew build
```

Готовый JAR окажется в `build/libs/`.

## Стек

- Minecraft 1.21.4
- Fabric Loader 0.16.10
- Fabric API 0.119.2
- Java 21
