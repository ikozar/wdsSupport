Web data services support
==========

Facilities:
- dynamic build JPA pagination Query, build restriction, using property chain
- get result collection of JPA Entity, Tuple or any DTO

Test it:
- tests contains in-memory db

Requirements:
- to receive result as DTOs configure XML mapping in dozer-style

---------------------------------------------------------

Автогенерации вебдатасервисов SOA, REST (CRUD + filtered collection) в форме JSON и XML (см. Контракт REST сервисов.rtf)

Автогенерация JPQL по значениям фильтров (sp.parameter("subdivision.store.teritory.name", "Москва"))

Автогенерация select и fetch JPQL при получении DTO, конвертация Entry в DTO (необходима настройка маппинга полей в Dozer или Orika)

