package pl.edu.agh.ki.covid19tablet.state.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldStateId

@Repository
interface SliderFieldStateRepository : CrudRepository<SliderFieldState, SliderFieldStateId>
