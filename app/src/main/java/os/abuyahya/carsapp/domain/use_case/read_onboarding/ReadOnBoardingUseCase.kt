package os.abuyahya.carsapp.domain.use_case.read_onboarding

import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.data.repository.Repository

class ReadOnBoardingUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}
