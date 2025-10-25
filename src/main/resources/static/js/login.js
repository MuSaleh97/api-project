// Comprehensive Login Page JavaScript - Following Detailed Specification

class LoginForm {
    constructor() {
        this.form = document.getElementById('loginForm');
        this.identifierInput = document.getElementById('identifier');
        this.passwordInput = document.getElementById('password');
        this.rememberMeInput = document.getElementById('rememberMe');
        this.loginButton = document.getElementById('loginButton');
        this.globalError = document.getElementById('globalError');
        this.networkToast = document.getElementById('networkToast');

        this.init();
    }

    init() {
        this.setupEventListeners();
        this.setupPasswordToggle();
        this.setupClearButton();
        this.setupTooltips();
        this.validateForm(); // Initial validation
    }

    setupEventListeners() {
        // Form submission
        this.form.addEventListener('submit', this.handleSubmit.bind(this));

        // Real-time validation
        this.identifierInput.addEventListener('input', this.handleIdentifierInput.bind(this));
        this.identifierInput.addEventListener('blur', this.validateIdentifier.bind(this));

        this.passwordInput.addEventListener('input', this.handlePasswordInput.bind(this));
        this.passwordInput.addEventListener('blur', this.validatePassword.bind(this));

        // Form validation on any change
        this.form.addEventListener('input', this.validateForm.bind(this));
        this.form.addEventListener('change', this.validateForm.bind(this));

        // Keyboard navigation
        this.setupKeyboardNavigation();
    }

    setupPasswordToggle() {
        const passwordToggle = document.querySelector('.password-toggle');

        passwordToggle.addEventListener('click', () => {
            const isPressed = passwordToggle.getAttribute('aria-pressed') === 'true';
            const newPressed = !isPressed;

            passwordToggle.setAttribute('aria-pressed', newPressed);
            passwordToggle.setAttribute('aria-label', newPressed ? 'Hide password' : 'Show password');

            this.passwordInput.type = newPressed ? 'text' : 'password';
        });
    }

    setupClearButton() {
        const clearButton = document.querySelector('.clear-button');

        this.identifierInput.addEventListener('input', () => {
            if (this.identifierInput.value.length > 0) {
                clearButton.style.display = 'block';
            } else {
                clearButton.style.display = 'none';
            }
        });

        clearButton.addEventListener('click', () => {
            this.identifierInput.value = '';
            this.identifierInput.focus();
            clearButton.style.display = 'none';
            this.clearFieldError('identifier');
            this.validateForm();
        });
    }

    setupTooltips() {
        // Tooltip functionality is handled via CSS hover
        // This can be enhanced for touch devices if needed
    }

    setupKeyboardNavigation() {
        const formElements = [
            this.identifierInput,
            this.passwordInput,
            this.rememberMeInput,
            document.querySelector('.forgot-link'),
            this.loginButton,
            document.querySelector('.signup-prompt a')
        ];

        this.form.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' && e.target.tagName !== 'BUTTON') {
                e.preventDefault();
                const currentIndex = formElements.indexOf(e.target);
                if (currentIndex < formElements.length - 1) {
                    formElements[currentIndex + 1].focus();
                } else {
                    this.loginButton.click();
                }
            }
        });
    }

    handleIdentifierInput() {
        this.clearFieldError('identifier');
        this.hideGlobalError();
    }

    handlePasswordInput() {
        this.clearFieldError('password');
        this.hideGlobalError();
    }

    validateIdentifier() {
        const identifier = this.identifierInput.value.trim();

        if (!identifier) {
            this.showFieldError('identifier', 'Email or phone is required.');
            return false;
        }

        if (!this.isValidEmail(identifier) && !this.isValidPhone(identifier)) {
            this.showFieldError('identifier', 'Enter a valid email or phone.');
            return false;
        }

        this.clearFieldError('identifier');
        return true;
    }

    validatePassword() {
        const password = this.passwordInput.value;

        if (!password) {
            this.showFieldError('password', 'Password is required.');
            return false;
        }

        if (password.length < 8) {
            this.showFieldError('password', 'Password must be at least 8 characters.');
            return false;
        }

        this.clearFieldError('password');
        return true;
    }

    validateForm() {
        const identifierValid = this.isFieldValid('identifier');
        const passwordValid = this.isFieldValid('password');

        const isFormValid = identifierValid && passwordValid;
        this.loginButton.disabled = !isFormValid;

        return isFormValid;
    }

    isFieldValid(fieldName) {
        const field = document.getElementById(fieldName);
        const value = field.value.trim();

        switch (fieldName) {
            case 'identifier':
                return value && (this.isValidEmail(value) || this.isValidPhone(value));
            case 'password':
                return value && value.length >= 8;
            default:
                return false;
        }
    }

    isValidEmail(email) {
        // Basic RFC email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    isValidPhone(phone) {
        // E.164 format: +{country}{number}
        const phoneRegex = /^\+[1-9]\d{7,14}$/;
        return phoneRegex.test(phone);
    }

    async handleSubmit(event) {
        event.preventDefault();

        // Hide any existing errors
        this.hideGlobalError();
        this.hideNetworkToast();

        // Validate form
        if (!this.validateForm()) {
            return;
        }

        // Show loading state
        this.setLoadingState(true);

        try {
            const formData = this.getFormData();
            const response = await this.submitLogin(formData);

            if (response.success) {
                this.handleLoginSuccess(response.data);
            } else {
                this.handleLoginError(response.error);
            }
        } catch (error) {
            console.error('Login error:', error);
            this.handleNetworkError();
        } finally {
            this.setLoadingState(false);
        }
    }

    getFormData() {
        return {
            identifier: this.identifierInput.value.trim(),
            password: this.passwordInput.value,
            rememberMe: this.rememberMeInput.checked
        };
    }

    async submitLogin(formData) {
        // Determine if identifier is email or phone
        const isEmail = this.isValidEmail(formData.identifier);

        const requestData = {
            [isEmail ? 'email' : 'phone']: formData.identifier,
            password: formData.password
        };

        // Note: rememberMe would typically be handled via session/cookie settings
        // This is just for demonstration
        if (formData.rememberMe) {
            localStorage.setItem('rememberLogin', 'true');
        }

        const response = await fetch('/account/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const data = await response.json();
            return { success: true, data: data };
        } else if (response.status === 401 || response.status === 403) {
            // Authentication error
            return { success: false, error: 'auth' };
        } else {
            // Other error
            const data = await response.json().catch(() => ({}));
            return {
                success: false,
                error: 'other',
                message: data.enDescription || data.arDescription || 'Login failed'
            };
        }
    }

    handleLoginSuccess(data) {
        // Store login data if needed
        if (data) {
            sessionStorage.setItem('userSession', JSON.stringify(data));
        }

        // Redirect to dashboard or intended page
        const redirectUrl = new URLSearchParams(window.location.search).get('redirect') || '/dashboard';
        window.location.href = redirectUrl;
    }

    handleLoginError(errorType) {
        if (errorType === 'auth') {
            this.showGlobalError();
        } else {
            this.showGlobalError('An unexpected error occurred. Please try again.');
        }
    }

    handleNetworkError() {
        this.showNetworkToast();
    }

    setLoadingState(isLoading) {
        this.form.classList.toggle('loading', isLoading);
        this.loginButton.disabled = isLoading || !this.validateForm();

        const buttonText = this.loginButton.querySelector('.button-text');
        const buttonSpinner = this.loginButton.querySelector('.button-spinner');

        if (isLoading) {
            buttonText.style.display = 'none';
            buttonSpinner.style.display = 'flex';
        } else {
            buttonText.style.display = 'block';
            buttonSpinner.style.display = 'none';
        }
    }

    showFieldError(fieldName, message) {
        const field = document.getElementById(fieldName);
        const errorElement = document.getElementById(`${fieldName}-error`);

        field.setAttribute('aria-invalid', 'true');
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }

    clearFieldError(fieldName) {
        const field = document.getElementById(fieldName);
        const errorElement = document.getElementById(`${fieldName}-error`);

        field.removeAttribute('aria-invalid');
        errorElement.textContent = '';
        errorElement.style.display = 'none';
    }

    showGlobalError(customMessage = null) {
        this.globalError.style.display = 'block';

        if (customMessage) {
            this.globalError.querySelector('.error-body').textContent = customMessage;
        }

        // Focus management for screen readers
        this.globalError.focus();
    }

    hideGlobalError() {
        this.globalError.style.display = 'none';
    }

    showNetworkToast() {
        this.networkToast.style.display = 'block';

        // Auto-hide after 5 seconds
        setTimeout(() => {
            this.hideNetworkToast();
        }, 5000);
    }

    hideNetworkToast() {
        this.networkToast.style.display = 'none';
    }
}

// Auto-fill detection and enhancements
class LoginEnhancements {
    constructor() {
        this.init();
    }

    init() {
        this.setupAutofillDetection();
        this.setupAccessibilityEnhancements();
        this.setupFormAnimations();
    }

    setupAutofillDetection() {
        // Detect browser autofill and adjust styling
        const inputs = document.querySelectorAll('input');

        inputs.forEach(input => {
            input.addEventListener('animationstart', (e) => {
                if (e.animationName === 'onAutoFillStart') {
                    input.classList.add('autofilled');
                }
            });

            input.addEventListener('animationend', (e) => {
                if (e.animationName === 'onAutoFillCancel') {
                    input.classList.remove('autofilled');
                }
            });
        });
    }

    setupAccessibilityEnhancements() {
        // Enhanced keyboard navigation
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                // Close any open tooltips or modals
                const activeElement = document.activeElement;
                if (activeElement && activeElement.blur) {
                    activeElement.blur();
                }
            }
        });

        // Improve focus visibility
        document.addEventListener('focusin', (e) => {
            if (e.target.matches('input, button, a, [tabindex]')) {
                e.target.classList.add('keyboard-focused');
            }
        });

        document.addEventListener('focusout', (e) => {
            e.target.classList.remove('keyboard-focused');
        });

        // Mouse interaction removes keyboard focus styling
        document.addEventListener('mousedown', (e) => {
            if (e.target.matches('input, button, a, [tabindex]')) {
                e.target.classList.remove('keyboard-focused');
            }
        });
    }

    setupFormAnimations() {
        // Subtle entrance animations for form elements
        const animateElements = document.querySelectorAll('.form-group, .form-row, .login-button, .signup-prompt');

        animateElements.forEach((element, index) => {
            element.style.opacity = '0';
            element.style.transform = 'translateY(20px)';

            setTimeout(() => {
                element.style.transition = 'all 0.6s cubic-bezier(0.16, 1, 0.3, 1)';
                element.style.opacity = '1';
                element.style.transform = 'translateY(0)';
            }, index * 100 + 200);
        });
    }
}

// Add autofill detection styles
const style = document.createElement('style');
style.textContent = `
    @keyframes onAutoFillStart {
        from { /*empty*/ }
        to { /*empty*/ }
    }

    @keyframes onAutoFillCancel {
        from { /*empty*/ }
        to { /*empty*/ }
    }

    input:-webkit-autofill,
    input:-webkit-autofill:hover,
    input:-webkit-autofill:focus {
        -webkit-animation-name: onAutoFillStart;
        animation-name: onAutoFillStart;
        -webkit-animation-duration: 1ms;
        animation-duration: 1ms;
    }

    input:not(:-webkit-autofill) {
        -webkit-animation-name: onAutoFillCancel;
        animation-name: onAutoFillCancel;
        -webkit-animation-duration: 1ms;
        animation-duration: 1ms;
    }

    .keyboard-focused {
        outline: 2px solid #7AA7FF !important;
        outline-offset: 2px !important;
    }

    .autofilled {
        background-color: rgba(246, 212, 123, 0.1) !important;
    }
`;
document.head.appendChild(style);

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new LoginForm();
    new LoginEnhancements();

    // Check for remember me preference
    if (localStorage.getItem('rememberLogin') === 'true') {
        document.getElementById('rememberMe').checked = true;
    }

    // Handle browser back button
    window.addEventListener('pageshow', (event) => {
        if (event.persisted) {
            // Reset form state if page was loaded from cache
            document.getElementById('loginForm').reset();
            document.getElementById('loginButton').disabled = true;
        }
    });
});

