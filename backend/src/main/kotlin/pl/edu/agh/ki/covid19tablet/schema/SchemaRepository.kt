package pl.edu.agh.ki.covid19tablet.schema

import org.springframework.data.repository.CrudRepository

interface SchemaRepository : CrudRepository<Schema, SchemaId>
