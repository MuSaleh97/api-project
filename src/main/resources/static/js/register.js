// Registration Page JavaScript
class RegistrationForm {
    constructor() {
        this.form = document.getElementById('registrationForm');
        this.errorMessage = document.getElementById('errorMessage');
        this.submitButton = this.form.querySelector('.primary-button');

        this.init();
    }

    init() {
        this.attachEventListeners();
        this.setupRealTimeValidation();
    }

    attachEventListeners() {
        this.form.addEventListener('submit', this.handleSubmit.bind(this));

        // Real-time validation for password confirmation
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        confirmPassword.addEventListener('input', () => {
            this.validatePasswordMatch();
        });

        password.addEventListener('input', () => {
            this.validatePasswordMatch();
        });

        // Phone number formatting
        const phoneInput = document.getElementById('phone');
        phoneInput.addEventListener('input', this.formatPhoneNumber.bind(this));

        // Email validation
        const emailInput = document.getElementById('email');
        emailInput.addEventListener('blur', this.validateEmail.bind(this));
    }

    setupRealTimeValidation() {
        const inputs = this.form.querySelectorAll('input, select');

        inputs.forEach(input => {
            input.addEventListener('blur', () => {
                this.validateField(input);
            });

            input.addEventListener('input', () => {
                if (input.classList.contains('invalid')) {
                    this.validateField(input);
                }
            });
        });
    }

    validateField(field) {
        const value = field.value.trim();
        let isValid = true;

        // Remove previous validation classes
        field.classList.remove('valid', 'invalid');

        switch (field.type) {
            case 'text':
                if (field.id === 'fullName') {
                    // Validate full name (at least 2 words, each at least 2 characters)
                    const nameParts = value.split(' ').filter(part => part.length > 0);
                    isValid = nameParts.length >= 2 && nameParts.every(part => part.length >= 2);
                } else {
                    isValid = value.length >= 2;
                }
                break;
            case 'email':
                isValid = this.isValidEmail(value);
                break;
            case 'tel':
                isValid = this.isValidPhone(value);
                break;
            case 'password':
                isValid = this.isValidPassword(value);
                break;
            case 'date':
                isValid = this.isValidAge(value);
                break;
            default:
                isValid = value.length > 0;
        }

        if (field.tagName === 'SELECT') {
            isValid = value !== '';
        }

        field.classList.add(isValid ? 'valid' : 'invalid');
        return isValid;
    }

    validatePasswordMatch() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const confirmField = document.getElementById('confirmPassword');

        if (confirmPassword.length > 0) {
            const isMatch = password === confirmPassword;
            confirmField.classList.remove('valid', 'invalid');
            confirmField.classList.add(isMatch ? 'valid' : 'invalid');

            if (!isMatch) {
                this.showError('Passwords do not match');
            } else {
                this.clearError();
            }

            return isMatch;
        }
        return true;
    }

    isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    isValidPhone(phone) {
        // Remove all non-digits
        const cleaned = phone.replace(/\D/g, '');
        // Accept phones between 7-15 digits (international format)
        return cleaned.length >= 7 && cleaned.length <= 15;
    }

    isValidPassword(password) {
        // At least 8 characters, contains letter and number
        return password.length >= 8 &&
               /[a-zA-Z]/.test(password) &&
               /\d/.test(password);
    }

    isValidAge(dateOfBirth) {
        if (!dateOfBirth) return false;

        const birthDate = new Date(dateOfBirth);
        const today = new Date();
        const age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();

        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age >= 13 && age <= 120; // Reasonable age range
    }

    formatPhoneNumber(event) {
        let value = event.target.value.replace(/\D/g, '');

        // Handle international format
        if (value.startsWith('962')) {
            // Jordan format: +962 79 123 4567
            value = value.replace(/(\d{3})(\d{2})(\d{3})(\d{4})/, '+$1 $2 $3 $4');
        } else if (value.startsWith('1') && value.length === 11) {
            // US format: +1 (555) 123-4567
            value = value.replace(/(\d{1})(\d{3})(\d{3})(\d{4})/, '+$1 ($2) $3-$4');
        } else if (value.length >= 10) {
            // Generic international format
            const country = value.slice(0, -10);
            const number = value.slice(-10);
            value = `+${country} ${number.replace(/(\d{3})(\d{3})(\d{4})/, '$1 $2 $3')}`;
        }

        event.target.value = value;
    }

    validateEmail(event) {
        const email = event.target.value.trim();
        if (email && !this.isValidEmail(email)) {
            this.showError('Please enter a valid email address');
            event.target.classList.add('invalid');
        } else {
            this.clearError();
            if (email) event.target.classList.add('valid');
        }
    }

    async handleSubmit(event) {
        event.preventDefault();

        // Clear previous errors
        this.clearError();

        // Validate all fields
        const formData = this.getFormData();
        const validation = this.validateForm(formData);

        if (!validation.isValid) {
            this.showError(validation.message);
            this.highlightInvalidFields();
            return;
        }

        // Show loading state
        this.setLoadingState(true);

        try {
            const response = await this.submitRegistration(formData);

            if (response.success) {
                this.handleSuccess();
            } else {
                this.showError(response.message || 'Registration failed. Please try again.');
            }
        } catch (error) {
            console.error('Registration error:', error);
            this.showError('Network error. Please check your connection and try again.');
        } finally {
            this.setLoadingState(false);
        }
    }

    getFormData() {
        return {
            fullName: document.getElementById('fullName').value.trim(),
            email: document.getElementById('email').value.trim(),
            phone: document.getElementById('phone').value.replace(/\D/g, ''), // Remove formatting
            gender: document.getElementById('gender').value,
            dob: document.getElementById('dob').value,
            password: document.getElementById('password').value,
            confirmPassword: document.getElementById('confirmPassword').value
        };
    }

    validateForm(data) {
        // Check required fields
        const requiredFields = ['fullName', 'email', 'phone', 'gender', 'dob', 'password'];

        for (const field of requiredFields) {
            if (!data[field]) {
                return { isValid: false, message: `Please fill in all required fields` };
            }
        }

        // Validate full name (at least 2 words)
        const nameParts = data.fullName.trim().split(' ');
        if (nameParts.length < 2 || nameParts.some(part => part.length < 2)) {
            return { isValid: false, message: 'Please enter your full name (first and last name)' };
        }

        // Validate email
        if (!this.isValidEmail(data.email)) {
            return { isValid: false, message: 'Please enter a valid email address' };
        }

        // Validate phone
        if (!this.isValidPhone(data.phone)) {
            return { isValid: false, message: 'Please enter a valid phone number' };
        }

        // Validate password
        if (!this.isValidPassword(data.password)) {
            return { isValid: false, message: 'Password must be at least 8 characters with letters and numbers' };
        }

        // Validate password match
        if (data.password !== data.confirmPassword) {
            return { isValid: false, message: 'Passwords do not match' };
        }

        // Validate age
        if (!this.isValidAge(data.dob)) {
            return { isValid: false, message: 'Please enter a valid date of birth (age 13-120)' };
        }

        return { isValid: true };
    }

    async submitRegistration(formData) {
        // Format phone number for API
        let phone = formData.phone;
        if (!phone.startsWith('+')) {
            // Try to detect country and format
            if (phone.length === 9 && phone.startsWith('7')) {
                phone = '+962' + phone; // Jordan
            } else if (phone.length === 10 && phone.startsWith('1')) {
                phone = '+1' + phone; // US
            } else {
                phone = '+' + phone; // Generic international
            }
        }

        const requestData = {
            fullName: formData.fullName,
            email: formData.email,
            phone: phone,
            gender: formData.gender, // Now sends enum value directly (MALE, FEMALE, OTHER)
            dob: formData.dob,
            password: formData.password,
            confirmPassword: formData.confirmPassword
        };

        const response = await fetch('/account/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData)
        });

        const data = await response.json();

        if (response.ok) {
            return { success: true, data: data };
        } else {
            // Handle API error response
            if (data.enDescription || data.arDescription) {
                return { success: false, message: data.enDescription || data.arDescription };
            } else {
                return { success: false, message: 'Registration failed. Please try again.' };
            }
        }
    }

    highlightInvalidFields() {
        const inputs = this.form.querySelectorAll('input, select');
        inputs.forEach(input => {
            if (!this.validateField(input)) {
                input.focus();
                return false; // Focus on first invalid field
            }
        });
    }

    setLoadingState(isLoading) {
        if (isLoading) {
            this.form.classList.add('loading');
            this.submitButton.disabled = true;
        } else {
            this.form.classList.remove('loading');
            this.submitButton.disabled = false;
        }
    }

    handleSuccess() {
        this.form.classList.add('success');
        this.showError('Registration successful! Please verify your email and phone number.', 'success');

        // Redirect to verification page after 2 seconds
        setTimeout(() => {
            window.location.href = '/verify-account';
        }, 2000);
    }

    showError(message, type = 'error') {
        this.errorMessage.textContent = message;
        this.errorMessage.style.color = type === 'success' ? '#4CAF50' : '#E53935';
        this.errorMessage.style.display = 'block';

        // Auto-hide error after 5 seconds
        setTimeout(() => {
            if (type === 'error') {
                this.clearError();
            }
        }, 5000);
    }

    clearError() {
        this.errorMessage.textContent = '';
        this.errorMessage.style.display = 'none';
    }
}

// Enhanced form animations and interactions
class FormAnimations {
    constructor() {
        this.init();
    }

    init() {
        this.animateInputs();
        this.addHoverEffects();
    }

    animateInputs() {
        const inputs = document.querySelectorAll('.form-group input, .form-group select');

        inputs.forEach((input, index) => {
            input.style.opacity = '0';
            input.style.transform = 'translateY(20px)';

            setTimeout(() => {
                input.style.transition = 'all 0.6s cubic-bezier(0.165, 0.84, 0.44, 1)';
                input.style.opacity = '1';
                input.style.transform = 'translateY(0)';
            }, index * 100);
        });
    }

    addHoverEffects() {
        const button = document.querySelector('.primary-button');

        // Button pulse effect (simplified)
        button.addEventListener('mouseenter', () => {
            button.style.animation = 'pulse 1s infinite';
        });

        button.addEventListener('mouseleave', () => {
            button.style.animation = 'none';
        });
    }
}

// Add pulse animation to CSS
const style = document.createElement('style');
style.textContent = `
    @keyframes pulse {
        0% { box-shadow: 0 0 0 0 rgba(255, 140, 0, 0.7); }
        70% { box-shadow: 0 0 0 10px rgba(255, 140, 0, 0); }
        100% { box-shadow: 0 0 0 0 rgba(255, 140, 0, 0); }
    }
`;
document.head.appendChild(style);

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new RegistrationForm();
    new FormAnimations();

    // Add smooth scroll behavior
    document.documentElement.style.scrollBehavior = 'smooth';

    // Add keyboard navigation
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' && e.target.tagName !== 'TEXTAREA') {
            const form = document.getElementById('registrationForm');
            const inputs = Array.from(form.querySelectorAll('input, select, button'));
            const currentIndex = inputs.indexOf(e.target);

            if (currentIndex < inputs.length - 1) {
                e.preventDefault();
                inputs[currentIndex + 1].focus();
            }
        }
    });
});

