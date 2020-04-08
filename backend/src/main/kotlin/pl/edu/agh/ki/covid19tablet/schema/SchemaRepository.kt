package pl.edu.agh.ki.covid19tablet.schema

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchemaRepository : CrudRepository<Schema, SchemaId>
