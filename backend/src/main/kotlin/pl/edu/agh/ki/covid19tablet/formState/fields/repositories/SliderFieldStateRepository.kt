package pl.edu.agh.ki.covid19tablet.formState.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.SliderFieldStateId

@Repository
interface SliderFieldStateRepository : CrudRepository<SliderFieldState, SliderFieldStateId>
