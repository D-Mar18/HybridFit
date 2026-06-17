package com.athallah.hybridfit.data.local

import android.content.Context

class OnboardingPreferenceStore(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun hasCompletedIntroSurvey(userId: Long?): Boolean {
        if (userId == null) return false
        return preferences.getBoolean(surveyKey(userId), false)
    }

    fun setCompletedIntroSurvey(userId: Long, completed: Boolean) {
        preferences.edit()
            .putBoolean(surveyKey(userId), completed)
            .apply()
    }

    companion object {
        private const val PREF_NAME = "hybridfit_onboarding"

        private fun surveyKey(userId: Long): String = "completed_intro_survey_$userId"
    }
}
