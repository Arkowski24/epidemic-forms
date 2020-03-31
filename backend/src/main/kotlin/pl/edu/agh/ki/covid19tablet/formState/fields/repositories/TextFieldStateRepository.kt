package pl.edu.agh.ki.covid19tablet.formState.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.TextFieldStateId

@Repository
interface TextFieldStateRepository : CrudRepository<TextFieldState, TextFieldStateId>
