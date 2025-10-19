// Animation and UI Enhancement JavaScript
class AnimationManager {
    constructor() {
        this.init();
    }

    init() {
        this.setupScrollAnimations();
        this.setupHoverEffects();
        this.setupFormAnimations();
        this.setupPageTransitions();
        this.setupLoadingAnimations();
    }

    setupScrollAnimations() {
        // Intersection Observer for scroll animations
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('animate-in');
                }
            });
        }, observerOptions);

        // Observe elements for animation
        const animatedElements = document.querySelectorAll('.stat-card, .dashboard-card, .account-card, .transfer-card, .bill-card');
        animatedElements.forEach(el => {
            observer.observe(el);
        });
    }

    setupHoverEffects() {
        // Enhanced hover effects for cards
        const cards = document.querySelectorAll('.stat-card, .account-card, .transfer-card, .bill-card, .dashboard-card');
        
        cards.forEach(card => {
            card.addEventListener('mouseenter', () => {
                this.addHoverEffect(card);
            });
            
            card.addEventListener('mouseleave', () => {
                this.removeHoverEffect(card);
            });
        });

        // Button hover effects
        const buttons = document.querySelectorAll('.btn');
        buttons.forEach(button => {
            button.addEventListener('mouseenter', () => {
                this.addButtonHoverEffect(button);
            });
            
            button.addEventListener('mouseleave', () => {
                this.removeButtonHoverEffect(button);
            });
        });
    }

    setupFormAnimations() {
        // Form input focus animations
        const inputs = document.querySelectorAll('input, textarea, select');
        
        inputs.forEach(input => {
            input.addEventListener('focus', () => {
                this.addInputFocusEffect(input);
            });
            
            input.addEventListener('blur', () => {
                this.removeInputFocusEffect(input);
            });

            // Floating label effect
            input.addEventListener('input', () => {
                this.handleFloatingLabel(input);
            });
        });
    }

    setupPageTransitions() {
        // Smooth page transitions
        const links = document.querySelectorAll('a[href$=".html"]');
        
        links.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                this.transitionToPage(link.href);
            });
        });
    }

    setupLoadingAnimations() {
        // Loading spinner animation
        this.createLoadingSpinner();
    }

    addHoverEffect(element) {
        element.style.transform = 'translateY(-8px) scale(1.02)';
        element.style.boxShadow = '0 20px 40px rgba(255, 107, 53, 0.2)';
        element.style.borderColor = '#ff6b35';
        
        // Add glow effect
        element.style.boxShadow += ', 0 0 20px rgba(255, 107, 53, 0.3)';
    }

    removeHoverEffect(element) {
        element.style.transform = '';
        element.style.boxShadow = '';
        element.style.borderColor = '';
    }

    addButtonHoverEffect(button) {
        if (button.classList.contains('btn-primary')) {
            button.style.background = 'linear-gradient(135deg, #ff8c42 0%, #ff6b35 100%)';
            button.style.transform = 'translateY(-2px)';
        }
    }

    removeButtonHoverEffect(button) {
        if (button.classList.contains('btn-primary')) {
            button.style.background = '';
            button.style.transform = '';
        }
    }

    addInputFocusEffect(input) {
        const wrapper = input.closest('.input-wrapper');
        if (wrapper) {
            wrapper.style.transform = 'scale(1.02)';
            wrapper.style.transition = 'transform 0.2s ease';
        }
    }

    removeInputFocusEffect(input) {
        const wrapper = input.closest('.input-wrapper');
        if (wrapper) {
            wrapper.style.transform = '';
        }
    }

    handleFloatingLabel(input) {
        const wrapper = input.closest('.form-group');
        if (wrapper) {
            const label = wrapper.querySelector('label');
            if (input.value.trim() !== '') {
                label.classList.add('floating');
            } else {
                label.classList.remove('floating');
            }
        }
    }

    transitionToPage(url) {
        // Create transition overlay
        const overlay = document.createElement('div');
        overlay.className = 'page-transition-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
            z-index: 9999;
            display: flex;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.3s ease;
        `;

        // Add loading spinner
        const spinner = document.createElement('div');
        spinner.className = 'transition-spinner';
        spinner.innerHTML = `
            <div class="spinner">
                <i class="fas fa-university"></i>
            </div>
            <p>Loading...</p>
        `;
        overlay.appendChild(spinner);

        document.body.appendChild(overlay);

        // Animate in
        setTimeout(() => {
            overlay.style.opacity = '1';
        }, 10);

        // Navigate after animation
        setTimeout(() => {
            window.location.href = url;
        }, 500);
    }

    createLoadingSpinner() {
        const style = document.createElement('style');
        style.textContent = `
            .transition-spinner {
                text-align: center;
                color: #ff6b35;
            }
            
            .spinner {
                width: 60px;
                height: 60px;
                margin: 0 auto 20px;
                border: 3px solid #404040;
                border-top: 3px solid #ff6b35;
                border-radius: 50%;
                animation: spin 1s linear infinite;
            }
            
            .spinner i {
                font-size: 24px;
                margin-top: 15px;
                animation: pulse 1.5s ease-in-out infinite;
            }
            
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            
            @keyframes pulse {
                0%, 100% { opacity: 1; }
                50% { opacity: 0.5; }
            }
            
            .animate-in {
                animation: slideInUp 0.6s ease-out;
            }
            
            @keyframes slideInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            .floating {
                transform: translateY(-20px) scale(0.85);
                color: #ff6b35 !important;
            }
            
            .notification-content {
                display: flex;
                align-items: center;
                gap: 10px;
            }
        `;
        document.head.appendChild(style);
    }

    // Utility methods for other components
    fadeIn(element, duration = 300) {
        element.style.opacity = '0';
        element.style.transition = `opacity ${duration}ms ease`;
        
        setTimeout(() => {
            element.style.opacity = '1';
        }, 10);
    }

    fadeOut(element, duration = 300) {
        element.style.transition = `opacity ${duration}ms ease`;
        element.style.opacity = '0';
        
        setTimeout(() => {
            element.style.display = 'none';
        }, duration);
    }

    slideIn(element, direction = 'left', duration = 300) {
        const directions = {
            left: 'translateX(-100%)',
            right: 'translateX(100%)',
            up: 'translateY(-100%)',
            down: 'translateY(100%)'
        };

        element.style.transform = directions[direction];
        element.style.transition = `transform ${duration}ms ease`;
        
        setTimeout(() => {
            element.style.transform = 'translate(0, 0)';
        }, 10);
    }

    slideOut(element, direction = 'left', duration = 300) {
        const directions = {
            left: 'translateX(-100%)',
            right: 'translateX(100%)',
            up: 'translateY(-100%)',
            down: 'translateY(100%)'
        };

        element.style.transition = `transform ${duration}ms ease`;
        element.style.transform = directions[direction];
        
        setTimeout(() => {
            element.style.display = 'none';
        }, duration);
    }

    bounce(element, duration = 600) {
        element.style.animation = `bounceIn ${duration}ms ease-out`;
        
        setTimeout(() => {
            element.style.animation = '';
        }, duration);
    }

    shake(element, duration = 500) {
        element.style.animation = `shake ${duration}ms ease-in-out`;
        
        setTimeout(() => {
            element.style.animation = '';
        }, duration);
    }
}

// Initialize animation manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.animationManager = new AnimationManager();
});

// Add shake animation CSS
const shakeStyle = document.createElement('style');
shakeStyle.textContent = `
    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
        20%, 40%, 60%, 80% { transform: translateX(5px); }
    }
`;
document.head.appendChild(shakeStyle);
