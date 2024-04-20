package woowacourse.movie

import android.widget.ListView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.presentation.screening.ScreeningMovieActivity
import woowacourse.movie.presentation.screening.ScreeningMovieAdapter
import woowacourse.movie.presentation.screening.ScreeningMovieUiModel

class ScreeningMovieActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ScreeningMovieActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            val listView = activity.findViewById<ListView>(R.id.list_screening_movie)
            val items = listOf(ScreeningMovieUiModel(1, title = "해리"))
            listView.adapter = ScreeningMovieAdapter(activity, items)
        }
    }

    @Test
    @DisplayName("MainActivity 가 화면에 보여지는지 테스트")
    fun test_isActivityInView() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("ListView 가 화면에 보여 지는지 테스트")
    fun listviewTest() {
        onData(`is`(withItemContent(containsString("해리 포터와 마법사의 돌"))))
            .inAdapterView(withId(R.id.list_screening_movie))
            .atPosition(0)
            .onChildView(withId(R.id.tv_movie_running_time))
            .check(matches(withText("러닝타임: 152분")))
    }

    @Test
    @DisplayName("예메 확인 버튼을 누르면 영화 예매 화면 으로 넘어감")
    fun listviewTest2() {
        onData(`is`(withItemContent(containsString("해리 포터와 마법사의 돌"))))
            .inAdapterView(withId(R.id.list_screening_movie))
            .atPosition(0)
            .onChildView(withId(R.id.btn_movie_reservation))
            .perform(click())

        onView(withId(R.id.detail_movie)).check(matches(isDisplayed()))
    }

    private fun withItemContent(itemTextMatcher: Matcher<String>): Matcher<ScreeningMovieUiModel> {
        return object : TypeSafeMatcher<ScreeningMovieUiModel>(ScreeningMovieUiModel::class.java) {
            override fun matchesSafely(screeningMovieUiModel: ScreeningMovieUiModel): Boolean {
                return itemTextMatcher.matches(screeningMovieUiModel.title)
            }

            override fun describeTo(description: Description) {
                description.appendText("with item content matching: ")
                itemTextMatcher.describeTo(description)
            }
        }
    }
}
