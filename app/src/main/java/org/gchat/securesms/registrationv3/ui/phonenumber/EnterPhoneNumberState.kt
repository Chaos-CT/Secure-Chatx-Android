/*
 * Copyright 2024 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.gchat.securesms.registrationv3.ui.phonenumber

import org.gchat.securesms.registration.ui.countrycode.Country
import org.gchat.securesms.registrationv3.data.RegistrationRepository

/**
 * State holder for the phone number entry screen, including phone number and Play Services errors.
 */
data class EnterPhoneNumberState(
  val countryPrefixIndex: Int,
  val phoneNumber: String = "",
  val phoneNumberRegionCode: String,
  val mode: RegistrationRepository.E164VerificationMode = RegistrationRepository.E164VerificationMode.SMS_WITHOUT_LISTENER,
  val error: Error = Error.NONE,
  val country: Country? = null
) {
  enum class Error {
    NONE, INVALID_PHONE_NUMBER, PLAY_SERVICES_MISSING, PLAY_SERVICES_NEEDS_UPDATE, PLAY_SERVICES_TRANSIENT
  }
}
