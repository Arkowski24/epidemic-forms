package pl.edu.agh.ki.covid19tablet.formState.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.formState.fields.SignFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.SignFieldStateId

@Repository
interface SignFieldStateRepository : CrudRepository<SignFieldState, SignFieldStateId>
