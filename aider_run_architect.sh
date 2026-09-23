#!/bin/bash
set -e # Остановка скрипта при ошибках

# Конфигурация файлов и команд
CONVENTIONS="Conventions.md"
TASK_FILE="StructuredTaskDescription.md"
STRUCTURE_FILE="ProjectStructure.md"
TEST_FILE="UnitTests.md"
TEST_CMD="./gradlew compileDebugSources testDebugUnitTest"

AIDER_FLAGS="--architect
            --read $CONVENTIONS
            --read $TASK_FILE
            --read $STRUCTURE_FILE
            --read $TEST_FILE
            --no-detect-urls
            --yes"

echo "=================================================="
echo "🚀 [Уровень 4] Запуск автономного пайплайна с Архитектором"
echo "=================================================="

# Проверка наличия обязательных файлов
if [ ! -f "$TASK_FILE" ]; then
    echo "❌ Ошибка: Файл $TASK_FILE не найден!"
    exit 1
fi

if [ ! -f "$STRUCTURE_FILE" ]; then
    echo "❌ Ошибка: Файл $STRUCTURE_FILE не найден!"
    exit 1
fi

if [ ! -f "$TEST_FILE" ]; then
    echo "❌ Ошибка: Файл $TEST_FILE не найден!"
    exit 1
fi

if [ ! -f "$CONVENTIONS" ]; then
    echo "❌ Ошибка: Файл $CONVENTIONS не найден!"
    exit 1
fi

# 1. Проектирование проекта
echo "📦 [Этап 1/4] Проектирование структуры приложения"
aider $AIDER_FLAGS \
      --message "Спроектируй структуру приложения согласно StructuredTaskDescription.md.
                НЕ ПИШИ реализацию репозиториев, UI или тесты."

# 2. Этап Data & Domain
echo "📦 [Этап 2/4] Генерация Data & Domain слоев..."
aider $AIDER_FLAGS \
      --message "Создай классы Data и Domain слоев строго по структуре. 
                НЕ создавай UI и тесты."

# 3. Этап UI & ViewModel + Tests
echo "🎨 [Этап 3/4] Генерация UI слоя и Unit-тестов..."
aider $AIDER_FLAGS \
      --message "Реализуй UI слой и Unit-тесты для написанной логики." \
      --test-cmd "$TEST_CMD" \
      --auto-test

# 4. Финальная верификация
echo "🔍 [Этап 4/4] Проверка сборки..."
aider $AIDER_FLAGS \
      --message "Исправь ошибки компиляции или упавшие тесты, если они есть." \
      --test-cmd "$TEST_CMD" \
      --auto-test

echo "=================================================="
echo "✅ [Уровень 4] Пайплайн завершен!"
echo "=================================================="
