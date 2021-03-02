

// Для работы необходим пакет gen.python версии 2.1.2
// и парсер https://github.com/JetBrains-Research/pythonparser
// парсер должен быть добавлен в PATH
dependencies {
    api("com.github.gumtreediff", "gen.python", "2.1.2")
}

// однако все другие зависимости gumtree проекта имеют транзитивную зависимость на gen.core 2.1.0
// gen.python же был создани сразу с 2.1.2 и ничего не знает про 2.1.0
// поэтому надо разрешить конфликт, 
// !!! НО надо проверить, что astminer дальше работает корректно со всем, что завязано на пакет com.github.gumtreediff:gen.core
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.github.gumtreediff" && requested.name == "gen.core" && requested.version == "2.1.0") {
            useVersion("2.1.2")
        }
    }
}

