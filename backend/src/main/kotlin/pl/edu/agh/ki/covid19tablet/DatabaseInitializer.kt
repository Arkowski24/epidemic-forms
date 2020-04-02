package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleField
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField

@Component
@Profile("dev")
class DatabaseInitializer {

    @Bean
    fun initializeDatabase(schemaRepository: SchemaRepository) =
        CommandLineRunner {
            schemaRepository.save(
                Schema(
                    name = "My Schema",
                    fields = SchemaFields(
                        simple = listOf(
                            SimpleField(
                                fieldNumber = 0,
                                title = "Read document",
                                description = "This form is used by hospital."
                            )
                        ),
                        choice = listOf(
                            ChoiceField(
                                fieldNumber = 1,
                                title = "Favourite authors",
                                description = "Your favourite author is:",
                                choices = listOf("Kafka", "Prost", "Tolkien")
                            ),
                            ChoiceField(
                                fieldNumber = 2,
                                title = "Favourite authors pt.2",
                                description = "Your favourite author is:",
                                choices = listOf("Kafka", "Prost", "Tolkien"),
                                isMultiChoice = true
                            )
                        ),
                        slider = listOf(
                            SliderField(
                                fieldNumber = 3,
                                title = "Fancy slider",
                                description = "Please slide freely.",
                                minValue = 0.0,
                                maxValue = 100.0,
                                step = 5.0
                            ),
                            SliderField(
                                fieldNumber = 4,
                                fieldType = FieldType.BLOCKED,
                                title = "Fancy slider 2",
                                description = "This slider is blocked.",
                                minValue = 0.0,
                                maxValue = 100.0,
                                step = 5.0
                            )
                        ),
                        text = listOf(
                            TextField(
                                fieldNumber = 5,
                                fieldType = FieldType.HIDDEN,
                                title = "Hidden field",
                                description = "Patient cannot read this."
                            ),
                            TextField(
                                fieldNumber = 6,
                                title = "Easy question",
                                fieldType = FieldType.BLOCKED,
                                description = "How tall are you? - only employee can answer"
                            ),
                            TextField(
                                fieldNumber = 7,
                                title = "Hard question",
                                description = "What have you eaten today?",
                                isMultiline = true
                            )
                        )
                    ),
                    patientSignature = SignatureField(
                        title = "Sign field - Patient",
                        description = "I hereby agree everything is alright."
                    ),
                    employeeSignature = SignatureField(
                        title = "Sign field - Employee",
                        description = "I certify everything is all-right."
                    )
                )
            )
        }
}
