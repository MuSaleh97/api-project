package com.spring_api.back_end.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMsg {

    LOGIN_ERROR("The username or password is incorrect", "اسم المستخدم أو كلمة المرور غير صحيحة"),
    PASSWORD_DO_NOT_MATCH("Password and Confirm Password don't match", "كلمة المرور وتأكيد كلمة المرور غير متطابقتين"),
    EMAIL_ALREADY_USED("This email is already in use", "هذا البريد الإلكتروني قيد الاستخدام بالفعل"),
    MOBILE_NUMBER_ALREADY_USED("This mobile number is already in use", "هذا رقم الهاتف المحمول قيد الاستخدام بالفعل"),
    ACCOUNT_ALREADY_DELETE("This account has already been deleted", "لقد تم حذف هذا الحساب بالفعل"),
    ACCOUNT_NOT_FOUND("Your account was not found. Please check your account information", "لم يتم العثور على حسابك. يُرجى التحقق من معلومات حسابك"),
    OTP_SEND_ERROR("An internal error occurred. We were unable to send OTP to your email. Please try again.", "حدث خطأ داخلي. لم نتمكن من إرسال كلمة مرور لمرة واحدة (OTP) إلى بريدك الإلكتروني. يُرجى المحاولة مرة أخرى."),
    CHECK_OTP("Please check the OTP code", "يرجى التحقق من رمز OTP"),
    CHANGE_EMAIL("The data has been changed successfully, including the email. You must log out to reactivate the new email.", "لقد تم تغيير البيانات بنجاح ومنها الايميل يجب تسجيل الخروج لاعادة تفعيل الاميل الجديد"),
    IS_NOT_VERIFIED("The account is not verified", "الحساب غير موثق"),

    // New error messages for additional scenarios
    INVALID_LOCATION("Invalid location provided. Please check the city and country names.", "موقع غير صالح. يرجى التحقق من أسماء المدينة والبلد."),
    INVALID_ZONE_REGION("Invalid time zone region provided.", "منطقة زمنية غير صالحة."),
    EXTERNAL_API_ERROR("Unable to retrieve prayer times. Please try again later.", "تعذر استرداد أوقات الصلاة. يرجى المحاولة مرة أخرى لاحقاً."),
    INVALID_PRAYER_TIME_FORMAT("Invalid prayer time format received.", "تم استلام تنسيق وقت صلاة غير صالح."),
    NETWORK_ERROR("Network connection error. Please check your internet connection.", "خطأ في الاتصال بالشبكة. يرجى التحقق من اتصال الإنترنت."),
    INVALID_REQUEST_DATA("Invalid request data provided.", "تم تقديم بيانات طلب غير صالحة."),
    INTERNAL_SERVER_ERROR("An internal server error occurred. Please try again later.", "حدث خطأ داخلي في الخادم. يرجى المحاولة مرة أخرى لاحقاً."),
    MISSING_REQUIRED_FIELD("Required field is missing or empty.", "حقل مطلوب مفقود أو فارغ."),
    INVALID_DATE_TIME("Invalid date or time format.", "تنسيق تاريخ أو وقت غير صالح."),
    PRAYER_TIME_CALCULATION_ERROR("Error calculating prayer times for the specified location.", "خطأ في حساب أوقات الصلاة للموقع المحدد.");

    private final String enDescription;
    private final String arDescription;
}
