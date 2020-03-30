package pl.edu.agh.ki.covid19tablet.schema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO

@RestController
@RequestMapping("/schemas")
class SchemaController(
    private val schemaService: SchemaService
) {
    @GetMapping
    fun getAllSchemas(): List<SchemaDTO> =
        schemaService.getAllSchemas()

    @GetMapping("{schemaId}")
    fun getSchema(@RequestParam schemaId: SchemaId): ResponseEntity<SchemaDTO> =
        try {
            val schema = schemaService.getSchema(schemaId)
            ResponseEntity(schema, HttpStatus.OK)
        } catch (ex: SchemaNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
}
