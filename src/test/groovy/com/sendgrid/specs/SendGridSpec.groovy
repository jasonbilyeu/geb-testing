package com.sendgrid.specs

import com.sendgrid.pages.MainPage
import com.sendgrid.test.api.qatest.models.User
import geb.spock.GebSpec

import static com.sendgrid.test.api.qatest.util.ARandom.aRandom

class SendGridSpec extends GebSpec {
    MainPage mainPage

    def setup() {
        to MainPage
        mainPage = (MainPage) browser.page
    }

    def "Fails when sent empty username"() {
        given:
        User user = aRandom.user().username('').build()

        when:
        mainPage.populateForm(user)

        then:
        mainPage.assertValidationError('Username must be greater that 2 characters and less 12')
    }

    def "Fails when sent too short of a username"() {
        given:
        User user = aRandom.user().username(aRandom.getRandomChars(1)).build()

        when:
        mainPage.populateForm(user)

        then:
        mainPage.assertValidationError('Username must be greater that 2 characters and less 12')
    }

    def "Fails when sent too long of a username"() {
        given:
        User user = aRandom.user().username(aRandom.getRandomChars(13)).build()

        when:
        mainPage.populateForm(user)

        then:
        mainPage.assertValidationError('Username must be greater that 2 characters and less 12')
    }

    def "Creates a valid user"() {
        when:
        mainPage.populateForm(aRandom.user().build())

        then:
        mainPage.assertSuccess()
    }

    def "Fails when user sends empty password"() {
        when:
        mainPage.populateForm(aRandom.user().password('').build())

        then:
        mainPage.assertValidationError('Password field must be between 8 and 20 characters.')
    }

    def "Fails when user sends empty confirm password"() {
        given:
        to MainPage
        MainPage mainPage = (MainPage) browser.page

        when:
        mainPage.populateForm(aRandom.user().build(), '')

        then:
        mainPage.assertValidationError('Passwords don\'t match!')
    }

    def "Fails when user sends both passwords empty"() {
        when:
        mainPage.populateForm(aRandom.user().password('').build(), '')

        then:
        mainPage.assertValidationError('Password field must be between 8 and 20 characters.')
    }

    def "Fails when user enters too short of a password"() {
        when:
        mainPage.populateForm(aRandom.user().password(aRandom.getRandomChars(1, 7)).build())

        then:
        mainPage.assertValidationError('Password field must be between 8 and 20 characters.')
    }

    def "Fails when user enters too long of a password"() {
        when:
        mainPage.populateForm(aRandom.user().password(aRandom.getRandomChars(21)).build())

        then:
        mainPage.assertValidationError('Password field must be between 8 and 20 characters.')
    }

    def "Fails when user sends alpha only password"() {
        when:
        mainPage.populateForm(aRandom.user().password(aRandom.getRandomText(8, 20)).build())

        then:
        mainPage.assertValidationError('Password Must contain some numbers.')
    }

    def "Fails when user sends numeric password"() {
        when:
        mainPage.populateForm(aRandom.user().password(aRandom.getNumberText(aRandom.getNumberBetween(8, 20))).build())

        then:
        mainPage.assertValidationError('Password Must contain some letters.')
    }

    def "Fails when user sends empty email"() {
        when:
        mainPage.populateForm(aRandom.user().email('').build())

        then:
        mainPage.assertValidationError('Email Address is Invalid.')
    }

    def "Fails when user sends invalid email"() {
        when:
        mainPage.populateForm(aRandom.user().email(aRandom.getRandomChars(10)).build())

        then:
        mainPage.assertValidationError('Email Address is Invalid.')
    }

    def "Fails when user sends duplicate email"() {
        given:
        User user = aRandom.user().build()
        mainPage.populateForm(user)
        to MainPage
        mainPage = (MainPage) browser.page

        when:
        mainPage.populateForm(user)

        then:
        mainPage.assertDuplicateEmail()
    }
}
