<!-- This is a generated file. Use `./gradlew :architectureDoc` to generate -->
# Gradle platform architecture
```mermaid
    graph TD

subgraph core["core platform"]

core_runtime["core-runtime module"]
style core_runtime stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;

core_configuration["core-configuration module"]
style core_configuration stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;

core_execution["core-execution module"]
style core_execution stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;
end
style core fill:#c2e0f4,stroke:#3498db,stroke-width:2px;

documentation["documentation module"]
style documentation stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;

ide["ide module"]
style ide stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;

subgraph software["software platform"]
end
style software fill:#c2e0f4,stroke:#3498db,stroke-width:2px;
software --> core

subgraph jvm["jvm platform"]
end
style jvm fill:#c2e0f4,stroke:#3498db,stroke-width:2px;
jvm --> core
jvm --> software

subgraph extensibility["extensibility platform"]
end
style extensibility fill:#c2e0f4,stroke:#3498db,stroke-width:2px;
extensibility --> core
extensibility --> jvm

subgraph native["native platform"]
end
style native fill:#c2e0f4,stroke:#3498db,stroke-width:2px;
native --> core
native --> software

enterprise["enterprise module"]
style enterprise stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;

build_infrastructure["build-infrastructure module"]
style build_infrastructure stroke:#1abc9c,fill:#b1f4e7,stroke-width:2px;
```
