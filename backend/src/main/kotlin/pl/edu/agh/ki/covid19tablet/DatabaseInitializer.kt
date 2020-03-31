package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.SignField
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleField
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField

@Bean
@Profile("dev")
fun initializeDatabase(schemaRepository: SchemaRepository) =
    CommandLineRunner {
        schemaRepository.save(
            Schema(
                title = "My Schema",
                fields = SchemaFields(
                    simple = listOf(
                        SimpleField(
                            fieldNumber = 0,
                            description = "This form is used by hospital."
                        )
                    ),
                    choice = listOf(
                        ChoiceField(
                            fieldNumber = 1,
                            description = "Your favourite author is:",
                            choices = listOf("Kafka", "Prost", "Tolkien")
                        ),
                        ChoiceField(
                            fieldNumber = 2,
                            description = "Your favourite author is:",
                            choices = listOf("Kafka", "Prost", "Tolkien"),
                            isMultiChoice = true
                        )
                    ),
                    slider = listOf(
                        SliderField(
                            fieldNumber = 3,
                            description = "Please slide freely.",
                            minValue = 0.0,
                            maxValue = 100.0,
                            step = 5.0
                        )
                    ),
                    text = listOf(
                        TextField(
                            fieldNumber = 4,
                            description = "How tall are you?"
                        ),
                        TextField(
                            fieldNumber = 5,
                            description = "What have you eaten today?",
                            isMultiline = true
                        )
                    ),
                    sign = listOf(
                        SignField(
                            fieldNumber = 6,
                            description = "I hereby agree everything is alright."
                        )
                    )
                )
            )
        )
    }
