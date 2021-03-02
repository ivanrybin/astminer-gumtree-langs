

// для работы нужен парсер srcml https://www.srcml.org/#download
// 2 .so файла должны лежать в нужной папке со всеми .so файлами
// скрипт srcml должен быть добавлен в PATH
dependencies {
    api("com.github.gumtreediff", "gen.srcml", "2.1.2")

}

// другие зависимости gumtree проекта имеют транзитивную зависимость на gen.core 2.1.0
// надо проверить, что astminer дальше работает корректно со всем, что завязано на пакет com.github.gumtreediff:gen.core
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.github.gumtreediff" && requested.name == "gen.core" && requested.version == "2.1.0") {
            useVersion("2.1.2")
        }
    }
}

