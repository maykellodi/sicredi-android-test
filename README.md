# sicredi-android-test
Repositório contendo o projeto do processo seletivo do sicredi

## Architecture

Utilizei a mais recente arquitetura disponível para Android, uma adaptação do MVI para o MVVM, trazendo as vantagens Unidirectional Data Flow para dentro do modelo MVVM que estamos acostumados.

## External Libs

- Retrofit 2 -> Para chamadas http / consumir API Rest.
- Jetpack Navigation -> Para navegação entre fragments no modelo Single Activity.
- Glide -> Para carregar as imagens com cache, placeholders, efeitos, etc...
