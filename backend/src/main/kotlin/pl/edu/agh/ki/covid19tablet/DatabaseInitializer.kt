package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.device.Device
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
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole

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
                        simple = listOf(),
                        choice = listOf(
                            ChoiceField(
                                fieldNumber = 7,
                                fieldType = FieldType.HIDDEN,
                                title = "Częstość oddechu",
                                choices = listOf("10-20", "20-30", "30-40", ">40")
                            ),
                            ChoiceField(
                                fieldNumber = 8,
                                title = "Czy miał Pan(i) kontakt z osoba zakażoną koronawirusem?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 9,
                                title = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 10,
                                title = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 11,
                                title = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 12,
                                title = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 13,
                                title = "Czy pracuje Pan(i) w szpitalu lub przychodni lub zakładzie opiekuńczym?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 14,
                                title = "Czy ma Pan(i) podwyższona temperaturę?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 15,
                                title = "Czy ma Pan(i) kaszel?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            ),
                            ChoiceField(
                                fieldNumber = 16,
                                title = "Czy odczuwa Pan(i) duszność, brak tchu?",
                                choices = listOf("TAK", "NIE"),
                                required = true
                            )
                        ),
                        derived = listOf(
                            DerivedField(
                                fieldNumber = 2,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("PESEL", "Data urodzenia")
                            )
                        ),
                        slider = listOf(
                            SliderField(
                                fieldNumber = 3,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Temperatura",
                                minValue = 34.0,
                                maxValue = 43.0,
                                defaultValue = 36.6,
                                step = 0.1
                            ),
                            SliderField(
                                fieldNumber = 4,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Ciśnienie skurczowe",
                                minValue = 50.0,
                                maxValue = 300.0,
                                defaultValue = 120.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 5,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Tętno",
                                minValue = 20.0,
                                maxValue = 200.0,
                                defaultValue = 70.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 6,
                                fieldType = FieldType.HIDDEN,
                                inline = false,
                                title = "Saturacja",
                                minValue = 80.0,
                                maxValue = 100.0,
                                defaultValue = 95.0,
                                step = 1.0
                            )
                        ),
                        text = listOf(
                            TextField(
                                fieldNumber = 0,
                                title = "Nazwisko",
                                required = true
                            ),
                            TextField(
                                fieldNumber = 1,
                                title = "Imię",
                                required = true
                            ),
                            TextField(
                                fieldNumber = 17,
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
            schemaRepository.save(
                Schema(
                    name = "Tech Demo",
                    multiPage = false,
                    fields = SchemaFields(
                        simple = listOf(
                            SimpleField(
                                fieldNumber = 0,
                                fieldType = FieldType.NORMAL,
                                title = "Simple",
                                description = "Simple",
                                inline = false
                            ),
                            SimpleField(
                                fieldNumber = 1,
                                fieldType = FieldType.NORMAL,
                                title = "Simple - NoDesc",
                                inline = false
                            ),
                            SimpleField(
                                fieldNumber = 2,
                                fieldType = FieldType.NORMAL,
                                title = "Simple - Inline",
                                inline = true
                            ),
                            SimpleField(
                                fieldNumber = 3,
                                fieldType = FieldType.BLOCKED,
                                title = "Simple - Blocked",
                                description = "Simple",
                                inline = false
                            ),
                            SimpleField(
                                fieldNumber = 4,
                                fieldType = FieldType.NORMAL,
                                title = "Simple - NoDesc - Blocked",
                                inline = false
                            ),
                            SimpleField(
                                fieldNumber = 5,
                                fieldType = FieldType.BLOCKED,
                                title = "Simple - Inline - Blocked",
                                inline = true
                            )
                        ),

                        choice = listOf(
                            ChoiceField(
                                fieldNumber = 6,
                                fieldType = FieldType.NORMAL,
                                title = "Multi-choice",
                                description = "Multi-choice",
                                choices = listOf("A", "B", "C"),
                                inline = false
                            ),
                            ChoiceField(
                                fieldNumber = 7,
                                fieldType = FieldType.NORMAL,
                                title = "Multi-choice - Inline",
                                choices = listOf("A", "B", "C"),
                                inline = true
                            ),
                            ChoiceField(
                                fieldNumber = 8,
                                fieldType = FieldType.BLOCKED,
                                title = "Multi-choice - Blocked",
                                description = "Multi-choice - Blocked",
                                choices = listOf("A", "B", "C"),
                                inline = false
                            ),
                            ChoiceField(
                                fieldNumber = 9,
                                fieldType = FieldType.BLOCKED,
                                title = "Multi-choice - Inline - Blocked",
                                choices = listOf("A", "B", "C"),
                                inline = true
                            ),

                            ChoiceField(
                                fieldNumber = 10,
                                fieldType = FieldType.NORMAL,
                                title = "Single-choice",
                                description = "Single-choice",
                                choices = listOf("A", "B", "C"),
                                inline = false
                            ),
                            ChoiceField(
                                fieldNumber = 11,
                                fieldType = FieldType.NORMAL,
                                title = "Single-choice - Inline",
                                choices = listOf("A", "B", "C"),
                                inline = true
                            ),
                            ChoiceField(
                                fieldNumber = 12,
                                fieldType = FieldType.NORMAL,
                                title = "Single-choice - Inline - Two Values",
                                choices = listOf("A", "B"),
                                inline = true
                            ),
                            ChoiceField(
                                fieldNumber = 13,
                                fieldType = FieldType.BLOCKED,
                                title = "Single-choice - Blocked",
                                description = "Single-choice - Blocked",
                                choices = listOf("A", "B", "C"),
                                inline = false
                            ),
                            ChoiceField(
                                fieldNumber = 14,
                                fieldType = FieldType.BLOCKED,
                                title = "Single-choice - Inline - Blocked",
                                choices = listOf("A", "B", "C"),
                                inline = true
                            ),
                            ChoiceField(
                                fieldNumber = 15,
                                fieldType = FieldType.BLOCKED,
                                title = "Single-choice - Inline - Two Values - Blocked",
                                choices = listOf("A", "B"),
                                inline = true
                            )
                        ),
                        derived = listOf(
                            DerivedField(
                                fieldNumber = 16,
                                fieldType = FieldType.NORMAL,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("Derived 1", "Derived 2"),
                                descriptions = listOf("Derived 1", "Derived 2"),
                                inline = false
                            ),
                            DerivedField(
                                fieldNumber = 17,
                                fieldType = FieldType.NORMAL,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("Derived 1 - Inline", "Derived 2 - Inline"),
                                inline = true
                            ),
                            DerivedField(
                                fieldNumber = 18,
                                fieldType = FieldType.BLOCKED,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("Derived 1 - Blocked", "Derived 2 - Blocked"),
                                descriptions = listOf("Derived 1 - Blocked", "Derived 2 - Blocked"),
                                inline = false
                            ),
                            DerivedField(
                                fieldNumber = 19,
                                fieldType = FieldType.BLOCKED,
                                derivedType = DerivedType.BIRTHDAY_PESEL,
                                titles = listOf("Derived 1 - Inline - Blocked", "Derived 2 - Inline - Blocked"),
                                inline = true
                            )
                        ),

                        slider = listOf(
                            SliderField(
                                fieldNumber = 20,
                                fieldType = FieldType.NORMAL,
                                inline = false,
                                title = "Slider",
                                minValue = 0.0,
                                maxValue = 100.0,
                                defaultValue = 50.0,
                                step = 5.0
                            ),
                            SliderField(
                                fieldNumber = 21,
                                fieldType = FieldType.NORMAL,
                                inline = true,
                                title = "Slider - Inline",
                                minValue = 0.0,
                                maxValue = 100.0,
                                defaultValue = 50.0,
                                step = 5.0
                            ),
                            SliderField(
                                fieldNumber = 22,
                                fieldType = FieldType.BLOCKED,
                                inline = false,
                                title = "Slider - Blocked",
                                minValue = 0.0,
                                maxValue = 100.0,
                                defaultValue = 50.0,
                                step = 5.0
                            ),
                            SliderField(
                                fieldNumber = 23,
                                fieldType = FieldType.BLOCKED,
                                inline = true,
                                title = "Slider - Inline - Blocked",
                                minValue = 0.0,
                                maxValue = 100.0,
                                defaultValue = 50.0,
                                step = 5.0
                            )
                        ),
                        text = listOf(
                            TextField(
                                fieldNumber = 24,
                                fieldType = FieldType.NORMAL,
                                inline = false,
                                title = "Text",
                                description = "Text",
                                multiLine = false
                            ),
                            TextField(
                                fieldNumber = 25,
                                fieldType = FieldType.NORMAL,
                                inline = false,
                                title = "Text - Multiline",
                                description = "Text - Multiline",
                                multiLine = true
                            ),
                            TextField(
                                fieldNumber = 26,
                                fieldType = FieldType.NORMAL,
                                inline = true,
                                title = "Text - Inline",
                                description = "Text",
                                multiLine = false
                            ),
                            TextField(
                                fieldNumber = 27,
                                fieldType = FieldType.NORMAL,
                                inline = true,
                                title = "Text - Multiline - Inline",
                                multiLine = true
                            ),
                            TextField(
                                fieldNumber = 28,
                                fieldType = FieldType.BLOCKED,
                                inline = false,
                                title = "Text",
                                description = "Text - Blocked",
                                multiLine = false
                            ),
                            TextField(
                                fieldNumber = 29,
                                fieldType = FieldType.BLOCKED,
                                inline = false,
                                title = "Text - Multiline - Blocked",
                                description = "Text - Multiline - Blocked",
                                multiLine = true
                            ),
                            TextField(
                                fieldNumber = 30,
                                fieldType = FieldType.BLOCKED,
                                inline = true,
                                title = "Text - Inline - Blocked",
                                multiLine = false
                            ),
                            TextField(
                                fieldNumber = 31,
                                fieldType = FieldType.BLOCKED,
                                inline = true,
                                title = "Text - Multiline - Inline - Blocked",
                                multiLine = true
                            )
                        )
                    ),
                    patientSignature = SignatureField(
                        title = "Signature - Patient",
                        description = "Signature - Patient"
                    ),
                    employeeSignature = SignatureField(
                        title = "Signature - Employee",
                        description = "Signature - Employee."
                    )
                )
            )

            employeeRepository.save(
                Employee(
                    username = "user",
                    fullName = "Jeannette Kalyta",
                    passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                    role = EmployeeRole.EMPLOYEE
                )
            )
            employeeRepository.save(
                Employee(
                    username = "admin",
                    fullName = "Dr. Oetker",
                    passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                    role = EmployeeRole.ADMIN
                )
            )
            employeeRepository.save(
                Device(
                    username = "device",
                    fullName = "HAL-3000",
                    passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                    role = EmployeeRole.DEVICE
                )
            )
        }
}
