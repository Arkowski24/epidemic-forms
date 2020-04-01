package pl.edu.agh.ki.covid19tablet.state.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldStateId

@Repository
interface TextFieldStateRepository : CrudRepository<TextFieldState, TextFieldStateId>
