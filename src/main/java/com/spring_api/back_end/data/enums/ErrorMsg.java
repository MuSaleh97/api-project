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
    IS_NOT_VERIFIED("The account is not verified", "الحساب غير موثق"),;

    private final String enDescription;
    private final String arDescription;
}
