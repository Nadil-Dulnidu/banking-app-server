// Home Page Animations and Interactions
class HomeAnimations {
    constructor() {
        this.init();
    }

    init() {
        this.setupNavigation();
        this.setupScrollAnimations();
        this.setupCounterAnimations();
        this.setupFormAnimations();
        this.setupSmoothScrolling();
        this.setupParallaxEffects();
    }

    setupNavigation() {
        const hamburger = document.querySelector('.hamburger');
        const navMenu = document.querySelector('.nav-menu');
        const navLinks = document.querySelectorAll('.nav-link');

        // Mobile menu toggle
        if (hamburger && navMenu) {
            hamburger.addEventListener('click', () => {
                hamburger.classList.toggle('active');
                navMenu.classList.toggle('active');
            });

            // Close mobile menu when clicking on links
            navLinks.forEach(link => {
                link.addEventListener('click', () => {
                    hamburger.classList.remove('active');
                    navMenu.classList.remove('active');
                });
            });
        }

        // Header scroll effect
        window.addEventListener('scroll', () => {
            const header = document.querySelector('.header');
            if (window.scrollY > 100) {
                header.style.background = 'rgba(26, 26, 26, 0.98)';
                header.style.backdropFilter = 'blur(15px)';
            } else {
                header.style.background = 'rgba(26, 26, 26, 0.95)';
                header.style.backdropFilter = 'blur(10px)';
            }
        });
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
                    this.animateElement(entry.target);
                }
            });
        }, observerOptions);

        // Observe elements for animation
        const animatedElements = document.querySelectorAll(
            '.feature-card, .service-card, .contact-item, .about-text, .about-image'
        );
        
        animatedElements.forEach(el => {
            observer.observe(el);
        });

        // Special animations for hero elements
        this.animateHeroElements();
    }

    animateHeroElements() {
        const heroTitle = document.querySelector('.hero-title');
        const heroSubtitle = document.querySelector('.hero-subtitle');
        const heroButtons = document.querySelector('.hero-buttons');
        const heroImage = document.querySelector('.hero-image');

        if (heroTitle) {
            setTimeout(() => {
                heroTitle.style.animation = 'slideInLeft 1s ease-out';
            }, 200);
        }

        if (heroSubtitle) {
            setTimeout(() => {
                heroSubtitle.style.animation = 'slideInLeft 1s ease-out';
            }, 400);
        }

        if (heroButtons) {
            setTimeout(() => {
                heroButtons.style.animation = 'slideInLeft 1s ease-out';
            }, 600);
        }

        if (heroImage) {
            setTimeout(() => {
                heroImage.style.animation = 'slideInRight 1s ease-out';
            }, 800);
        }
    }

    animateElement(element) {
        const animationType = element.dataset.animation || 'fadeInUp';
        
        switch (animationType) {
            case 'fadeInUp':
                element.style.animation = 'fadeInUp 0.8s ease-out';
                break;
            case 'fadeInLeft':
                element.style.animation = 'fadeInLeft 0.8s ease-out';
                break;
            case 'fadeInRight':
                element.style.animation = 'fadeInRight 0.8s ease-out';
                break;
            case 'scaleIn':
                element.style.animation = 'scaleIn 0.8s ease-out';
                break;
            default:
                element.style.animation = 'fadeInUp 0.8s ease-out';
        }
    }

    setupCounterAnimations() {
        const counters = document.querySelectorAll('.stat-number');
        
        const counterObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    this.animateCounter(entry.target);
                    counterObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });

        counters.forEach(counter => {
            counterObserver.observe(counter);
        });
    }

    animateCounter(element) {
        const target = parseInt(element.dataset.target);
        const duration = 2000; // 2 seconds
        const increment = target / (duration / 16); // 60fps
        let current = 0;

        const timer = setInterval(() => {
            current += increment;
            if (current >= target) {
                current = target;
                clearInterval(timer);
            }
            
            // Format number with commas
            element.textContent = Math.floor(current).toLocaleString();
        }, 16);
    }

    setupFormAnimations() {
        const formInputs = document.querySelectorAll('.contact-form input, .contact-form textarea');
        
        formInputs.forEach(input => {
            // Focus animation
            input.addEventListener('focus', () => {
                input.parentElement.classList.add('focused');
                this.addInputFocusEffect(input);
            });

            // Blur animation
            input.addEventListener('blur', () => {
                if (!input.value) {
                    input.parentElement.classList.remove('focused');
                }
                this.removeInputFocusEffect(input);
            });

            // Input animation
            input.addEventListener('input', () => {
                this.handleInputAnimation(input);
            });
        });

        // Form submission animation
        const contactForm = document.querySelector('.contact-form');
        if (contactForm) {
            contactForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleFormSubmission(contactForm);
            });
        }
    }

    addInputFocusEffect(input) {
        const wrapper = input.parentElement;
        wrapper.style.transform = 'scale(1.02)';
        wrapper.style.transition = 'transform 0.2s ease';
    }

    removeInputFocusEffect(input) {
        const wrapper = input.parentElement;
        wrapper.style.transform = '';
    }

    handleInputAnimation(input) {
        if (input.value) {
            input.parentElement.classList.add('has-value');
        } else {
            input.parentElement.classList.remove('has-value');
        }
    }

    handleFormSubmission(form) {
        const submitBtn = form.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        
        // Show loading state
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Sending...';
        submitBtn.disabled = true;
        
        // Simulate form submission
        setTimeout(() => {
            submitBtn.innerHTML = '<i class="fas fa-check"></i> Sent!';
            submitBtn.style.background = 'var(--success-green)';
            
            // Reset after 2 seconds
            setTimeout(() => {
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
                submitBtn.style.background = '';
                form.reset();
            }, 2000);
        }, 1500);
    }

    setupSmoothScrolling() {
        const navLinks = document.querySelectorAll('a[href^="#"]');
        
        navLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                
                const targetId = link.getAttribute('href');
                const targetSection = document.querySelector(targetId);
                
                if (targetSection) {
                    const headerHeight = document.querySelector('.header').offsetHeight;
                    const targetPosition = targetSection.offsetTop - headerHeight;
                    
                    window.scrollTo({
                        top: targetPosition,
                        behavior: 'smooth'
                    });
                }
            });
        });
    }

    setupParallaxEffects() {
        window.addEventListener('scroll', () => {
            const scrolled = window.pageYOffset;
            const parallaxElements = document.querySelectorAll('.hero-background, .hero-overlay');
            
            parallaxElements.forEach(element => {
                const speed = element.dataset.speed || 0.5;
                element.style.transform = `translateY(${scrolled * speed}px)`;
            });
        });
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

    // Enhanced hover effects
    setupEnhancedHoverEffects() {
        const cards = document.querySelectorAll('.feature-card, .service-card');
        
        cards.forEach(card => {
            card.addEventListener('mouseenter', () => {
                this.addCardHoverEffect(card);
            });
            
            card.addEventListener('mouseleave', () => {
                this.removeCardHoverEffect(card);
            });
        });
    }

    addCardHoverEffect(card) {
        card.style.transform = 'translateY(-10px) scale(1.02)';
        card.style.boxShadow = '0 20px 40px rgba(255, 107, 53, 0.2)';
        card.style.borderColor = 'var(--primary-orange)';
        
        // Add glow effect
        card.style.boxShadow += ', 0 0 20px rgba(255, 107, 53, 0.3)';
    }

    removeCardHoverEffect(card) {
        card.style.transform = '';
        card.style.boxShadow = '';
        card.style.borderColor = '';
    }

    // Loading animations
    showLoading(element) {
        element.style.position = 'relative';
        element.style.overflow = 'hidden';
        
        const loader = document.createElement('div');
        loader.className = 'loading-overlay';
        loader.innerHTML = '<div class="spinner"></div>';
        
        element.appendChild(loader);
    }

    hideLoading(element) {
        const loader = element.querySelector('.loading-overlay');
        if (loader) {
            loader.remove();
        }
    }
}

// Initialize animations when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.homeAnimations = new HomeAnimations();
});

// Add additional CSS animations
const additionalStyles = document.createElement('style');
additionalStyles.textContent = `
    .animate-in {
        opacity: 1;
        transform: translateY(0);
    }
    
    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(30px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    @keyframes fadeInLeft {
        from {
            opacity: 0;
            transform: translateX(-30px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    @keyframes fadeInRight {
        from {
            opacity: 0;
            transform: translateX(30px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    @keyframes scaleIn {
        from {
            opacity: 0;
            transform: scale(0.8);
        }
        to {
            opacity: 1;
            transform: scale(1);
        }
    }
    
    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
        20%, 40%, 60%, 80% { transform: translateX(5px); }
    }
    
    .loading-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(26, 26, 26, 0.8);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 10;
    }
    
    .spinner {
        width: 40px;
        height: 40px;
        border: 3px solid var(--tertiary-black);
        border-top: 3px solid var(--primary-orange);
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }
    
    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }
    
    .form-group.focused {
        transform: scale(1.02);
    }
    
    .form-group.has-value input,
    .form-group.has-value textarea {
        border-color: var(--primary-orange);
    }
    
    .hamburger.active .bar:nth-child(2) {
        opacity: 0;
    }
    
    .hamburger.active .bar:nth-child(1) {
        transform: translateY(7px) rotate(45deg);
    }
    
    .hamburger.active .bar:nth-child(3) {
        transform: translateY(-7px) rotate(-45deg);
    }
`;

document.head.appendChild(additionalStyles);
