package com.sendgrid.pages

import com.sendgrid.test.api.qatest.models.User
import geb.Page
import spock.util.concurrent.PollingConditions

class MainPage extends Page {

    def conditions = new PollingConditions(timeout: 10)

    static at = { title == "QA Evaluation" }

    static content = {
        username { $('#username') }
        email { $('#email') }
        password { $('#password') }
        confpassword { $('#confpassword') }
        submitButton { $('button') }
        error { $('#error') }
        signupMessage { $('.SignUpMessage') }
    }

    void populateForm(User user, String confirmPassword = user.password) {
        username.value(user.username)
        email.value(user.email)
        password.value(user.password)
        confpassword.value(confirmPassword)
        submitButton.click()

        conditions.eventually {
            assert signupMessage.text() != '' || error.text() != ''
        }
    }

    void assertValidationError(String expectedText) {
        assert error.text() == expectedText
    }

    void assertSuccess() {
        assert signupMessage.text().contains('Success!')
    }

    void assertDuplicateEmail() {
        assert signupMessage.text() == 'User with that email already exists'
    }
}
