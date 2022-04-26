package os.abuyahya.carsapp.domain.use_case.save_onboarding

import os.abuyahya.carsapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}
