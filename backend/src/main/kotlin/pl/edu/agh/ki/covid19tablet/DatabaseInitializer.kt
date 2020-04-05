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
                                title = "Proszę przeczytaj dokładnie.",
                                description = "Ten formularz jest używany przez Szpital, w celu zapewnienia bezpieczeństwa Tobie i nam."
                            ),
                            SimpleField(
                                fieldNumber = 9,
                                title = "Pytania epidemiczne",
                                description = "Ta część zawiera pytania dotyczące potencjalnej ekspozycji na wirusa powodującego chorobę COVID-19. " +
                                        "Udzielenie odpowiedzi zgodnie z prawdą jest kluczowe do zapewnienia bezpieczeństwa Tobie i Personelowi Szpitala."
                            )
                        ),
                        choice = listOf(
                            ChoiceField(
                                fieldNumber = 7,
                                fieldType = FieldType.BLOCKED,
                                title = "Częstość oddechu",
                                description = "Częstość oddechu pacjenta:",
                                choices = listOf("10-20", "20-30", "30-40", ">40")
                            ),
                            ChoiceField(
                                fieldNumber = 10,
                                title = "Pytanie 1",
                                description = "Czy miał Pan(i) kontakt z osoba zakażoną koronawirusem?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 11,
                                title = "Pytanie 2",
                                description = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 12,
                                title = "Pytanie 3",
                                description = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 13,
                                title = "Pytanie 4",
                                description = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 14,
                                title = "Pytanie 5",
                                description = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 15,
                                title = "Pytanie 6",
                                description = "Czy pracuje Pan(i) w szpitalu lub przychodni lub zakładzie opiekuńczym?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 16,
                                title = "Pytanie 7",
                                description = "Czy ma Pan(i) podwyższona temperaturę?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 17,
                                title = "Pytanie 8",
                                description = "Czy ma Pan(i) kaszel?",
                                choices = listOf("TAK", "NIE")
                            ),
                            ChoiceField(
                                fieldNumber = 18,
                                title = "Pytanie 9",
                                description = "Czy odczuwa Pan(i) duszność, brak tchu?",
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
                                fieldNumber = 4,
                                fieldType = FieldType.BLOCKED,
                                title = "Temperatura",
                                description = "Temperatura zmierzona pacjentowi:",
                                minValue = 30.0,
                                maxValue = 45.0,
                                step = 0.01
                            ),
                            SliderField(
                                fieldNumber = 5,
                                fieldType = FieldType.BLOCKED,
                                title = "Ciśnienie skurczowe",
                                description = "Ciśnienie skurczowe zmierzone pacjentowi:",
                                minValue = 50.0,
                                maxValue = 300.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 6,
                                fieldType = FieldType.BLOCKED,
                                title = "Tętno",
                                description = "Tętno zmierzone pacjentowi:",
                                minValue = 20.0,
                                maxValue = 200.0,
                                step = 10.0
                            ),
                            SliderField(
                                fieldNumber = 8,
                                fieldType = FieldType.BLOCKED,
                                title = "Saturacja",
                                description = "Saturacja krwii pacjenta:",
                                minValue = 50.0,
                                maxValue = 100.0,
                                step = 5.0
                            )
                        ),
                        text = listOf(
                            TextField(
                                fieldNumber = 1,
                                title = "Nazwisko",
                                description = "Nazwisko pacjenta:"
                            ),
                            TextField(
                                fieldNumber = 2,
                                title = "Imiona",
                                description = "Imiona pacjenta:"
                            ),
                            TextField(
                                fieldNumber = 19,
                                fieldType = FieldType.HIDDEN,
                                title = "Dodatkowe objawy",
                                description = "Miejsce na wprowadzenie zaobserwowanych objawów: (pacjent nie widzi tego pola)",
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
