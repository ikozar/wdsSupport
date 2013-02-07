Web data services support
==========

Facilities:
- dynamic build JPA pagination Query
- return result collection in a form JPA entity, JPA Tuple
  or any ValueObject on demand
- build restriction for query root entity properties, or
  xToOne joined entity for any joined level (propA.propB.propC)

Test it:
- tests contains in-memory db

Requirements:
- if your want to receive query result of ValueObject type,
  your need to configure dozer mapping between JPA entity and ValueObject
  and from ValueObject to Tuple

