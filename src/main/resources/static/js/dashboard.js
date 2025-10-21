// Dashboard JavaScript with Mock Data and CRUD Operations
class DashboardManager {
    constructor() {
        this.baseURL = window.location.origin;
        this.currentUser = null;
        this.accounts = [];
        this.transfers = [];
        this.bills = [];
        this.init();
    }

    init() {
        this.loadMockData();
        this.setupEventListeners();
        this.loadUserProfile();
        this.loadDashboardData();
    }

    loadMockData() {
        // Mock user dat

        // Mock transfers data
        this.transfers = [
            {
                id: 1,
                fromAccount: 'ACC001234567',
                toAccount: 'ACC001234568',
                beneficiaryName: 'Jane Smith',
                amount: 500.00,
                description: 'Monthly rent payment',
                status: 'COMPLETED',
                transferType: 'INTRA_BANK',
                createdAt: new Date('2024-01-15T10:30:00')
            },
            {
                id: 2,
                fromAccount: 'ACC001234568',
                toAccount: 'ACC009876543',
                beneficiaryName: 'ABC Company',
                amount: 1200.00,
                description: 'Invoice payment',
                status: 'PENDING',
                transferType: 'INTER_BANK',
                createdAt: new Date('2024-01-16T14:20:00')
            },
            {
                id: 3,
                fromAccount: 'ACC001234567',
                toAccount: 'ACC005555555',
                beneficiaryName: 'Investment Fund',
                amount: 2000.00,
                description: 'Investment contribution',
                status: 'COMPLETED',
                transferType: 'INTER_BANK',
                createdAt: new Date('2024-01-17T09:15:00')
            }
        ];

        // Mock bills data
        this.bills = [
            {
                id: 1,
                billerName: 'Electric Company',
                amount: 125.50,
                paymentFrequency: 'MONTHLY',
                nextPaymentDate: new Date('2024-02-01'),
                accountId: 1
            },
            {
                id: 2,
                billerName: 'Internet Provider',
                amount: 79.99,
                paymentFrequency: 'MONTHLY',
                nextPaymentDate: new Date('2024-02-05'),
                accountId: 2
            },
            {
                id: 3,
                billerName: 'Insurance Company',
                amount: 450.00,
                paymentFrequency: 'MONTHLY',
                nextPaymentDate: new Date('2024-02-10'),
                accountId: 1
            }
        ];
    }

    setupEventListeners() {
        // Navigation menu
        const navItems = document.querySelectorAll('.nav-item');
        navItems.forEach(item => {
            item.addEventListener('click', () => {
                const section = item.getAttribute('data-section');
                this.showSection(section);
                this.updateActiveNavItem(item);
            });
        });

        // Modal forms
        this.setupModalForms();
    }

    setupModalForms() {
        // Transfer form
        const transferForm = document.getElementById('transferForm');
        if (transferForm) {
            transferForm.addEventListener('submit', (e) => this.handleTransferSubmit(e));
        }

        // Account form
        const accountForm = document.getElementById('accountForm');
        if (accountForm) {
            accountForm.addEventListener('submit', (e) => this.handleAccountSubmit(e));
        }

        // Bill payment form
        const billPaymentForm = document.getElementById('billPaymentForm');
        if (billPaymentForm) {
            billPaymentForm.addEventListener('submit', (e) => this.handleBillPaymentSubmit(e));
        }

        // Profile form
        const profileForm = document.getElementById('profileForm');
        if (profileForm) {
            profileForm.addEventListener('submit', (e) => this.handleProfileSubmit(e));
        }
    }

    loadUserProfile() {
        const userNameElement = document.getElementById('userName');
        const userRoleElement = document.getElementById('userRole');

        if (userNameElement && userRoleElement) {
            userNameElement.textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
            userRoleElement.textContent = this.currentUser.userRole;
        }
    }

    loadDashboardData() {
        this.loadRecentTransactions();
        this.loadTransfers();
        this.loadBills();
        this.populateAccountSelects();
        this.loadProfile();
    }

    loadRecentTransactions() {
        const container = document.getElementById('recentTransactions');
        if (!container) return;

        const recentTransactions = this.transfers.slice(0, 5);
        
        container.innerHTML = recentTransactions.map(transfer => `
            
        `).join('');
    }


    loadTransfers() {
        const container = document.getElementById('transfersGrid');
        if (!container) return;

    }


    loadBills() {
        const container = document.getElementById('billsGrid');
        if (!container) return;
    }

    loadProfile() {
        const profileForm = document.getElementById('profileForm');
        if (!profileForm) return;
    }

    populateAccountSelects() {
        const selects = document.querySelectorAll('select[id*="Account"], select[name*="accountId"]');
        selects.forEach(select => {
            select.innerHTML = '<option value="">Select Account</option>' +
                this.accounts.map(account => 
                    `<option value="${account.id}">${account.accountNumber} (${account.accountType})</option>`
                ).join('');
        });
    }

    showSection(sectionName) {
        // Hide all sections
        const sections = document.querySelectorAll('.content-section');
        sections.forEach(section => section.classList.remove('active'));

        // Show selected section
        const targetSection = document.getElementById(`${sectionName}-section`);
        if (targetSection) {
            targetSection.classList.add('active');
            this.updatePageTitle(sectionName);
        }
    }

    updatePageTitle(sectionName) {
        const pageTitle = document.getElementById('pageTitle');
        const pageSubtitle = document.getElementById('pageSubtitle');
        
        const titles = {
            overview: { title: 'Dashboard Overview', subtitle: 'Welcome back! Here\'s what\'s happening with your accounts.' },
            accounts: { title: 'Account Management', subtitle: 'Manage your bank accounts and view balances.' },
            transfers: { title: 'Fund Transfers', subtitle: 'Send money and track your transfer history.' },
            bills: { title: 'Bill Payments', subtitle: 'Pay bills and manage recurring payments.' },
            profile: { title: 'Profile Management', subtitle: 'Update your personal information and settings.' },
            settings: { title: 'Settings', subtitle: 'Configure your account preferences and security.' }
        };

        if (pageTitle && pageSubtitle && titles[sectionName]) {
            pageTitle.textContent = titles[sectionName].title;
            pageSubtitle.textContent = titles[sectionName].subtitle;
        }
    }

    updateActiveNavItem(activeItem) {
        const navItems = document.querySelectorAll('.nav-item');
        navItems.forEach(item => item.classList.remove('active'));
        activeItem.classList.add('active');
    }

    // Modal functions
    showTransferModal() {
        console.log('showTransferModal called');
        this.showModal('transferModal');
    }

    showAddAccountModal() {
        console.log('showAddAccountModal called');
        this.showModal('accountModal');
    }

    showBillPaymentModal() {
        console.log('showBillPaymentModal called');
        this.showModal('billPaymentModal');
    }

    showModal(modalId) {
        console.log('Showing modal:', modalId);
        const overlay = document.getElementById('modalOverlay');
        const modal = document.getElementById(modalId);
        
        if (overlay && modal) {
            // First, hide all other modals
            const allModals = document.querySelectorAll('.modal');
            console.log('Found modals:', allModals.length);
            allModals.forEach(m => {
                m.style.display = 'none';
                m.classList.remove('active');
            });
            
            // Then show the requested modal
            overlay.classList.add('active');
            modal.style.display = 'block';
            modal.classList.add('active');
            console.log('Modal displayed:', modalId);
            
            // Ensure modal content is visible with a small delay
            setTimeout(() => {
                this.ensureModalContent(modalId);
            }, 50);
            
            // Add animation
            if (window.animationManager) {
                window.animationManager.fadeIn(overlay);
                window.animationManager.bounce(modal);
            }
        } else {
            console.error('Modal not found:', modalId, 'Overlay:', overlay, 'Modal:', modal);
        }
    }

    ensureModalContent(modalId) {
        console.log('Ensuring modal content for:', modalId);
        // Ensure form elements are properly visible and functional
        const modal = document.getElementById(modalId);
        if (modal) {
            console.log('Modal found, ensuring content visibility');
            // Make sure all form elements are visible
            const formElements = modal.querySelectorAll('input, select, textarea, button');
            console.log('Found form elements:', formElements.length);
            formElements.forEach(element => {
                element.style.display = '';
                element.style.visibility = 'visible';
                element.style.opacity = '1';
            });
            
            // Ensure form is properly reset and ready
            const form = modal.querySelector('form');
            if (form) {
                console.log('Form found, resetting and enabling elements');
                // Reset form to default state
                form.reset();
                // Ensure all form elements are enabled
                const inputs = form.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    input.disabled = false;
                    input.style.display = '';
                });
                console.log('Form elements enabled:', inputs.length);
            } else {
                console.log('No form found in modal');
            }
        } else {
            console.error('Modal not found for content ensuring:', modalId);
        }
    }

    closeModal() {
        console.log('Closing modal');
        const overlay = document.getElementById('modalOverlay');
        const modals = document.querySelectorAll('.modal');
        
        if (overlay) {
            overlay.classList.remove('active');
            
            // Add animation
            if (window.animationManager) {
                window.animationManager.fadeOut(overlay);
            }
            
            setTimeout(() => {
                // Hide all modals and reset their display
                console.log('Hiding all modals:', modals.length);
                modals.forEach(modal => {
                    modal.style.display = 'none';
                    modal.classList.remove('active');
                });
                console.log('All modals hidden');
            }, 300);
        }
    }

    // Form handlers
    async handleTransferSubmit(e) {
        e.preventDefault();
        
        const formData = new FormData(e.target);
        const transferData = {
            fromAccount: formData.get('fromAccount'),
            toAccount: formData.get('toAccount'),
            beneficiaryName: formData.get('beneficiaryName'),
            amount: parseFloat(formData.get('amount')),
            description: formData.get('description'),
            transferType: formData.get('transferType')
        };

        try {
            // Simulate API call
            await this.simulateApiCall();
            
            // Add to mock data
            const newTransfer = {
                id: this.transfers.length + 1,
                ...transferData,
                status: 'PENDING',
                createdAt: new Date()
            };
            
            this.transfers.unshift(newTransfer);
            this.loadTransfers();
            this.loadRecentTransactions();
            
            this.showSuccess('Transfer initiated successfully!');
            e.target.reset();
            this.closeModal();
            
        } catch (error) {
            this.showError('Transfer failed. Please try again.');
        }
    }

    async handleAccountSubmit(e) {
        e.preventDefault();
        
        const formData = new FormData(e.target);
        const accountData = {
            accountType: formData.get('accountType'),
            balance: parseFloat(formData.get('initialBalance')) || 0
        };

        try {
            // Simulate API call
            await this.simulateApiCall();
            
            // Add to mock data
            const newAccount = {
                id: this.accounts.length + 1,
                accountNumber: `ACC${String(Date.now()).slice(-9)}`,
                ...accountData,
                status: 'ACTIVE',
                userId: this.currentUser.id
            };
            
            this.accounts.push(newAccount);
            this.loadAccounts();
            this.populateAccountSelects();
            
            this.showSuccess('Account created successfully!');
            e.target.reset();
            this.closeModal();
            
        } catch (error) {
            this.showError('Account creation failed. Please try again.');
        }
    }

    async handleBillPaymentSubmit(e) {
        e.preventDefault();
        
        const formData = new FormData(e.target);
        const billData = {
            billerName: formData.get('billerName'),
            amount: parseFloat(formData.get('amount')),
            paymentFrequency: formData.get('paymentFrequency'),
            accountId: parseInt(formData.get('accountId'))
        };

        try {
            // Simulate API call
            await this.simulateApiCall();
            
            // Add to mock data
            const newBill = {
                id: this.bills.length + 1,
                ...billData,
                nextPaymentDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000) // 30 days from now
            };
            
            this.bills.push(newBill);
            this.loadBills();
            
            this.showSuccess('Bill payment set up successfully!');
            e.target.reset();
            this.closeModal();
            
        } catch (error) {
            this.showError('Bill payment setup failed. Please try again.');
        }
    }

    // Utility methods
    async simulateApiCall(delay = 1000) {
        return new Promise(resolve => setTimeout(resolve, delay));
    }

    showSuccess(message) {
        if (window.authManager) {
            window.authManager.showSuccess(message);
        }
    }

    showError(message) {
        if (window.authManager) {
            window.authManager.showError(message);
        }
    }
}

// Global functions for HTML onclick events
function showSection(sectionName) {
    if (window.dashboardManager) {
        window.dashboardManager.showSection(sectionName);
    }
}

function showTransferModal() {
    console.log('Global showTransferModal called');
    if (window.dashboardManager) {
        window.dashboardManager.showTransferModal();
    } else {
        console.error('DashboardManager not initialized');
    }
}

function showAddAccountModal() {
    console.log('Global showAddAccountModal called');
    if (window.dashboardManager) {
        window.dashboardManager.showAddAccountModal();
    } else {
        console.error('DashboardManager not initialized');
    }
}

function showBillPaymentModal() {
    console.log('Global showBillPaymentModal called');
    if (window.dashboardManager) {
        window.dashboardManager.showBillPaymentModal();
    } else {
        console.error('DashboardManager not initialized');
    }
}

function closeModal() {
    console.log('Global closeModal called');
    if (window.dashboardManager) {
        window.dashboardManager.closeModal();
    } else {
        console.error('DashboardManager not initialized');
    }
}

// Initialize dashboard manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM Content Loaded');
    if (document.body.classList.contains('dashboard-container') || 
        window.location.pathname.includes('dashboard')) {
        console.log('Initializing DashboardManager...');
        window.dashboardManager = new DashboardManager();
        console.log('DashboardManager initialized');
    } else {
        console.log('Not on dashboard page');
    }
});

// Fallback initialization if DOMContentLoaded already fired
if (document.readyState === 'loading') {
    console.log('Document still loading...');
} else {
    console.log('Document already loaded, checking for dashboard...');
    if (document.body.classList.contains('dashboard-container') || 
        window.location.pathname.includes('dashboard.html')) {
        if (!window.dashboardManager) {
            console.log('Initializing DashboardManager immediately...');
            window.dashboardManager = new DashboardManager();
        }
    }
}

// Force initialization after a short delay as a last resort
setTimeout(() => {
    if (!window.dashboardManager && 
        (document.body.classList.contains('dashboard-container') || 
         window.location.pathname.includes('dashboard.html'))) {
        console.log('Force initializing DashboardManager...');
        window.dashboardManager = new DashboardManager();
    }
}, 1000);

// Close modal when clicking outside
document.addEventListener('click', (e) => {
    const overlay = document.getElementById('modalOverlay');
    if (e.target === overlay) {
        closeModal();
    }
});

// Close modal with Escape key
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        closeModal();
    }
});
