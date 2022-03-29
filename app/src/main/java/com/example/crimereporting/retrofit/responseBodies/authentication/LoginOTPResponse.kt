package com.example.crimereporting.retrofit.responseBodies.authentication

import com.google.gson.annotations.SerializedName

/*
*  "status": "success",
  "message": "OTP sent successfully",
  "otp": {
    "sid": "VE686954f4cd166d82411274f43ccd7d28",
    "serviceSid": "VA2d451c3fbe4b589663ced3f86795ca06",
    "accountSid": "AC2aea8543b7dcdeb3850bbb4792fdcecb",
    "to": "+918223088814",
    "channel": "sms",
    "status": "pending",
    "valid": false,
    "lookup": {
      "carrier": {
        "mobile_country_code": "404",
        "type": "mobile",
        "error_code": null,
        "mobile_network_code": "78",
        "name": "IDEA Cellular Ltd"
      }
    },
    "amount": null,
    "payee": null,
    "sendCodeAttempts": [
      {
        "attempt_sid": "VL84ef52914358d70f5ac3921652befd9a",
        "channel": "sms",
        "time": "2022-02-14T20:07:19.358Z"
      }
    ],
    "dateCreated": "2022-02-14T20:07:19.000Z",
    "dateUpdated": "2022-02-14T20:07:19.000Z",
    "url": "https://verify.twilio.com/v2/Services/VA2d451c3fbe4b589663ced3f86795ca06/Verifications/VE686954f4cd166d82411274f43ccd7d28"
  }
* */
data class LoginOTPResponse(
    @SerializedName("status") val status: String = "",
     @SerializedName("message") val message : String = "",
     @SerializedName("token") val token : String = ""
)
