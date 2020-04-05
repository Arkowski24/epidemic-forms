package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleField
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository

@Component
@Profile("!prod")
class DatabaseInitializer {

    @Bean
    fun initializeDatabase(
        schemaRepository: SchemaRepository,
        employeeRepository: EmployeeRepository
    ) =
        CommandLineRunner {
            schemaRepository.save(
                Schema(
                    name = "Formularz epidemiczny",
                    multiPage = false,
                    fields = SchemaFields(
                        simple = listOf(
                            SimpleField(
                                fieldNumber = 0,
                                title = "Dane osobowe",
                                fieldType = FieldType.HIDDEN
                            ),
                            SimpleField(
                                fieldNumber = 4,
                                title = "Parametry życiowe",
                                fieldType = FieldType.HIDDEN
                            ),
                            SimpleField(
                                fieldNumber = 10,
                                title = "Pytania epidemiczne",
                                fieldType = FieldType.HIDDEN
                            )
                        ),
                        choice = listOf(
                            ChoiceField(
                                fieldNumber = 8,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Częstość oddechu",
                                description = "Częstość oddechu pacjenta:",
                                choices = listOf("10-20", "20-30", "30-40", ">40")
                            ),
                            ChoiceField(
                                fieldNumber = 11,
                                title = "Czy miał Pan(i) kontakt z osoba zakażoną koronawirusem?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 12,
                                title = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 13,
                                title = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 14,
                                title = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 15,
                                title = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 16,
                                title = "Czy pracuje Pan(i) w szpitalu lub przychodni lub zakładzie opiekuńczym?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 17,
                                title = "Czy ma Pan(i) podwyższona temperaturę?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 18,
                                title = "Czy ma Pan(i) kaszel?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 19,
                                title = "Czy odczuwa Pan(i) duszność, brak tchu?",
                                choices = listOf("TAK", "NIE")
                            )
                        ),
                        derived = listOf(
                            DerivedField(
                                fieldNumber = 3,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("PESEL", "Data urodzin"),
                                descriptions = listOf("PESEL pacjenta: ", "Data urodzin pacjenta:")
                            )
                        ),
                        slider = listOf(
                            SliderField(
                                fieldNumber = 5,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Temperatura",
                                minValue = 34.0,
                                maxValue = 43.0,
                                defaultValue = 36.6,
                                step = 0.1
                            ),
                            SliderField(
                                fieldNumber = 6,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Ciśnienie skurczowe",
                                minValue = 50.0,
                                maxValue = 300.0,
                                defaultValue = 120.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 7,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Tętno",
                                minValue = 20.0,
                                maxValue = 200.0,
                                defaultValue = 70.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 9,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Saturacja",
                                minValue = 80.0,
                                maxValue = 100.0,
                                defaultValue = 95.0,
                                step = 5.0
                            )
                        ),
                        text = listOf(
                            TextField(
                                fieldNumber = 1,
                                title = "Nazwisko"
                            ),
                            TextField(
                                fieldNumber = 2,
                                title = "Imiona"
                            ),
                            TextField(
                                fieldNumber = 20,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Dodatkowe objawy",
                                multiLine = true
                            )
                        )
                    ),
                    patientSignature = SignatureField(
                        title = "Podpis - Pacjent",
                        description = "Oświadczenie: mam świadomość odpowiedzialności karnej w przypadku podania nieprawdziwych informacji."
                    ),
                    employeeSignature = SignatureField(
                        title = "Podpis - Pracownik",
                        description = "Oświadczenie: potwierdzam prawidłowość wprowadzonych informacji."
                    )
                )
            )
            employeeRepository.save(
                Employee(
                    username = "user",
                    fullName = "Jeannette Kalyta",
                    passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq"
                )
            )
            employeeRepository.save(
                Employee(
                    username = "admin",
                    fullName = "Dr. Oetker",
                    passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq"
                )
            )
        }
}
