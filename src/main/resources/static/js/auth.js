// Authentication JavaScript
class AuthManager {
    constructor() {
        this.init();
    }

    init() {
        this.setupEventListeners();
    }

    setupEventListeners() {

        // Password toggle functionality
        this.setupPasswordToggle();
    }

    setupPasswordToggle() {
        const toggleButtons = document.querySelectorAll('.toggle-password');
        toggleButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                const input = e.target.closest('.input-wrapper').querySelector('input');
                const icon = e.target;

                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        });
    }
}

// Global functions for HTML onclick events
function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    const button = input.parentElement.querySelector('.toggle-password');
    const icon = button.querySelector('i');

    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// Initialize auth manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.authManager = new AuthManager();
});

document.head.appendChild(style);
