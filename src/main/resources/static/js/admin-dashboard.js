// Admin Dashboard JavaScript with Advanced Administrative Features
class AdminDashboardManager {
    constructor() {
        this.baseURL = window.location.origin;
        this.currentAdmin = null;
        this.users = [];
        this.accounts = [];
        this.transactions = [];
        this.auditLogs = [];
        this.init();
    }

    init() {
        console.log('Initializing AdminDashboardManager...');
        this.loadMockData();
        this.setupEventListeners();
        this.loadAdminProfile();
        this.loadDashboardData();
        console.log('AdminDashboardManager initialized successfully');
    }

    loadMockData() {
        // Mock admin data
        this.currentAdmin = {
            id: 1,
            username: 'admin_user',
            firstName: 'Admin',
            lastName: 'User',
            email: 'admin@securebank.com',
            phone: '+1-555-0001',
            address: 'Admin Office, SecureBank HQ',
            userRole: 'ADMIN'
        };

        // Mock users data
        this.users = [
            {
                id: 1,
                username: 'john_doe',
                firstName: 'John',
                lastName: 'Doe',
                email: 'john.doe@email.com',
                phone: '+1-555-0123',
                address: '123 Main St, Anytown, USA',
                userRole: 'CUSTOMER',
                status: 'ACTIVE',
                lastLogin: new Date('2024-01-17T09:15:00'),
                createdAt: new Date('2023-06-15T10:30:00')
            },
            {
                id: 2,
                username: 'jane_smith',
                firstName: 'Jane',
                lastName: 'Smith',
                email: 'jane.smith@email.com',
                phone: '+1-555-0124',
                address: '456 Oak Ave, Anytown, USA',
                userRole: 'CUSTOMER',
                status: 'ACTIVE',
                lastLogin: new Date('2024-01-16T14:20:00'),
                createdAt: new Date('2023-07-20T11:45:00')
            },
            {
                id: 3,
                username: 'bank_employee_1',
                firstName: 'Robert',
                lastName: 'Johnson',
                email: 'robert.johnson@securebank.com',
                phone: '+1-555-0125',
                address: '789 Pine St, Anytown, USA',
                userRole: 'BANK_EMPLOYEE',
                status: 'ACTIVE',
                lastLogin: new Date('2024-01-17T08:30:00'),
                createdAt: new Date('2023-05-10T09:15:00')
            },
            {
                id: 4,
                username: 'support_staff_1',
                firstName: 'Sarah',
                lastName: 'Wilson',
                email: 'sarah.wilson@securebank.com',
                phone: '+1-555-0126',
                address: '321 Elm St, Anytown, USA',
                userRole: 'SUPPORT_STAFF',
                status: 'ACTIVE',
                lastLogin: new Date('2024-01-17T10:45:00'),
                createdAt: new Date('2023-08-05T13:20:00')
            },
            {
                id: 5,
                username: 'suspended_user',
                firstName: 'Mike',
                lastName: 'Brown',
                email: 'mike.brown@email.com',
                phone: '+1-555-0127',
                address: '654 Maple Dr, Anytown, USA',
                userRole: 'CUSTOMER',
                status: 'SUSPENDED',
                lastLogin: new Date('2024-01-10T16:30:00'),
                createdAt: new Date('2023-09-12T14:10:00')
            }
        ];

        // Mock accounts data
        this.accounts = [
            {
                id: 1,
                accountNumber: 'ACC001234567',
                accountType: 'SAVINGS',
                balance: 12500.00,
                status: 'ACTIVE',
                userId: 1,
                ownerName: 'John Doe',
                createdAt: new Date('2023-06-15T10:30:00')
            },
            {
                id: 2,
                accountNumber: 'ACC001234568',
                accountType: 'CHECKING',
                balance: 2450.75,
                status: 'ACTIVE',
                userId: 1,
                ownerName: 'John Doe',
                createdAt: new Date('2023-06-15T10:35:00')
            },
            {
                id: 3,
                accountNumber: 'ACC001234569',
                accountType: 'BUSINESS',
                balance: 50000.00,
                status: 'ACTIVE',
                userId: 2,
                ownerName: 'Jane Smith',
                createdAt: new Date('2023-07-20T11:45:00')
            },
            {
                id: 4,
                accountNumber: 'ACC001234570',
                accountType: 'SAVINGS',
                balance: 8750.25,
                status: 'ACTIVE',
                userId: 2,
                ownerName: 'Jane Smith',
                createdAt: new Date('2023-07-20T11:50:00')
            },
            {
                id: 5,
                accountNumber: 'ACC001234571',
                accountType: 'CHECKING',
                balance: 0.00,
                status: 'SUSPENDED',
                userId: 5,
                ownerName: 'Mike Brown',
                createdAt: new Date('2023-09-12T14:10:00')
            }
        ];

        // Mock transactions data
        this.transactions = [
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
            },
            {
                id: 4,
                fromAccount: 'ACC001234569',
                toAccount: 'ACC001234570',
                beneficiaryName: 'Internal Transfer',
                amount: 1500.00,
                description: 'Business to personal transfer',
                status: 'COMPLETED',
                transferType: 'INTRA_BANK',
                createdAt: new Date('2024-01-17T11:30:00')
            },
            {
                id: 5,
                fromAccount: 'ACC001234571',
                toAccount: 'ACC001234567',
                beneficiaryName: 'John Doe',
                amount: 250.00,
                description: 'Payment',
                status: 'FAILED',
                transferType: 'INTRA_BANK',
                createdAt: new Date('2024-01-17T15:45:00')
            }
        ];

        // Mock audit logs data
        this.auditLogs = [
            {
                id: 1,
                timestamp: new Date('2024-01-17T15:45:00'),
                user: 'john_doe',
                action: 'LOGIN',
                level: 'INFO',
                details: 'User logged in successfully',
                ipAddress: '192.168.1.100'
            },
            {
                id: 2,
                timestamp: new Date('2024-01-17T15:30:00'),
                user: 'admin_user',
                action: 'CREATE',
                level: 'INFO',
                details: 'Created new user account',
                ipAddress: '192.168.1.1'
            },
            {
                id: 3,
                timestamp: new Date('2024-01-17T15:15:00'),
                user: 'jane_smith',
                action: 'UPDATE',
                level: 'INFO',
                details: 'Updated profile information',
                ipAddress: '192.168.1.101'
            },
            {
                id: 4,
                timestamp: new Date('2024-01-17T15:00:00'),
                user: 'suspended_user',
                action: 'LOGIN',
                level: 'WARN',
                details: 'Failed login attempt - account suspended',
                ipAddress: '192.168.1.102'
            },
            {
                id: 5,
                timestamp: new Date('2024-01-17T14:45:00'),
                user: 'bank_employee_1',
                action: 'DELETE',
                level: 'INFO',
                details: 'Deleted transaction record',
                ipAddress: '192.168.1.2'
            }
        ];
    }

    setupEventListeners() {
        // Navigation menu
        const navItems = document.querySelectorAll('.nav-item');
        console.log('Found nav items:', navItems.length);
        navItems.forEach(item => {
            item.addEventListener('click', () => {
                const section = item.getAttribute('data-section');
                console.log('Nav item clicked:', section);
                this.showSection(section);
                this.updateActiveNavItem(item);
            });
        });

        // Modal forms
        this.setupModalForms();

        // Search and filter functionality
        this.setupSearchAndFilters();
    }

    setupModalForms() {
        // Modal forms setup removed - no add user/account functionality
    }

    setupSearchAndFilters() {
        // User search and filters
        const userSearch = document.getElementById('userSearch');
        const roleFilter = document.getElementById('roleFilter');
        const statusFilter = document.getElementById('statusFilter');

        if (userSearch) {
            userSearch.addEventListener('input', () => this.filterUsers());
        }
        if (roleFilter) {
            roleFilter.addEventListener('change', () => this.filterUsers());
        }
        if (statusFilter) {
            statusFilter.addEventListener('change', () => this.filterUsers());
        }

        // Account search and filters
        const accountSearch = document.getElementById('accountSearch');
        const accountTypeFilter = document.getElementById('accountTypeFilter');
        const accountStatusFilter = document.getElementById('accountStatusFilter');

        if (accountSearch) {
            accountSearch.addEventListener('input', () => this.filterAccounts());
        }
        if (accountTypeFilter) {
            accountTypeFilter.addEventListener('change', () => this.filterAccounts());
        }
        if (accountStatusFilter) {
            accountStatusFilter.addEventListener('change', () => this.filterAccounts());
        }

        // Transaction search and filters
        const transactionSearch = document.getElementById('transactionSearch');
        const transactionStatusFilter = document.getElementById('transactionStatusFilter');
        const transactionTypeFilter = document.getElementById('transactionTypeFilter');
        const dateFilter = document.getElementById('dateFilter');

        if (transactionSearch) {
            transactionSearch.addEventListener('input', () => this.filterTransactions());
        }
        if (transactionStatusFilter) {
            transactionStatusFilter.addEventListener('change', () => this.filterTransactions());
        }
        if (transactionTypeFilter) {
            transactionTypeFilter.addEventListener('change', () => this.filterTransactions());
        }
        if (dateFilter) {
            dateFilter.addEventListener('change', () => this.filterTransactions());
        }

        // Audit logs search and filters
        const auditSearch = document.getElementById('auditSearch');
        const auditLevelFilter = document.getElementById('auditLevelFilter');
        const auditActionFilter = document.getElementById('auditActionFilter');
        const auditDateFilter = document.getElementById('auditDateFilter');

        if (auditSearch) {
            auditSearch.addEventListener('input', () => this.filterAuditLogs());
        }
        if (auditLevelFilter) {
            auditLevelFilter.addEventListener('change', () => this.filterAuditLogs());
        }
        if (auditActionFilter) {
            auditActionFilter.addEventListener('change', () => this.filterAuditLogs());
        }
        if (auditDateFilter) {
            auditDateFilter.addEventListener('change', () => this.filterAuditLogs());
        }
    }

    loadAdminProfile() {
        const adminNameElement = document.getElementById('adminName');
        const adminRoleElement = document.getElementById('adminRole');

        if (adminNameElement && adminRoleElement) {
            adminNameElement.textContent = `${this.currentAdmin.firstName} ${this.currentAdmin.lastName}`;
            adminRoleElement.textContent = this.currentAdmin.userRole;
        }
    }

    loadDashboardData() {
        this.loadRecentActivity();
        this.loadUsers();
        this.loadAccounts();
        this.loadTransactions();
        this.loadAuditLogs();
        this.loadSecurityLogs();
        this.populateUserSelects();
    }

    loadRecentActivity() {
        const container = document.getElementById('recentActivity');
        if (!container) return;

        const recentActivity = this.auditLogs.slice(0, 5);

        container.innerHTML = recentActivity.map(log => `
            <div class="activity-item">
                <div class="activity-icon">
                    <i class="fas fa-${this.getActivityIcon(log.action)}"></i>
                </div>
                <div class="activity-details">
                    <h4>${log.user} - ${log.action}</h4>
                    <p>${log.details}</p>
                    <span class="activity-time">${log.timestamp.toLocaleString()}</span>
                </div>
            </div>
        `).join('');
    }

    getActivityIcon(action) {
        const icons = {
            'LOGIN': 'sign-in-alt',
            'LOGOUT': 'sign-out-alt',
            'CREATE': 'plus',
            'UPDATE': 'edit',
            'DELETE': 'trash',
            'TRANSFER': 'exchange-alt',
            'PAYMENT': 'credit-card'
        };
        return icons[action] || 'info-circle';
    }

    loadUsers() {
        const container = document.getElementById('usersTableBody');
        if (!container) return;
    }

    loadAccounts() {
        const container = document.getElementById('accountsTableBody');
        if (!container) return;
    }

    loadTransactions() {
        const container = document.getElementById('transactionsTableBody');
        if (!container) return;
    }

    loadAuditLogs() {
        const container = document.getElementById('auditLogsTableBody');
        if (!container) return;

        container.innerHTML = this.auditLogs.map(log => `
            <tr>
                <td>${log.timestamp.toLocaleString()}</td>
                <td>${log.user}</td>
                <td>${log.action}</td>
                <td><span class="level-badge ${log.level.toLowerCase()}">${log.level}</span></td>
                <td>${log.details}</td>
                <td>${log.ipAddress}</td>
            </tr>
        `).join('');
    }

    loadSecurityLogs() {
        const container = document.getElementById('securityLogs');
        if (!container) return;

        const securityEvents = [
            {
                type: 'Failed Login',
                user: 'unknown_user',
                ip: '192.168.1.200',
                time: new Date('2024-01-17T15:30:00'),
                severity: 'high'
            },
            {
                type: 'Suspicious Transaction',
                user: 'john_doe',
                ip: '192.168.1.100',
                time: new Date('2024-01-17T14:45:00'),
                severity: 'medium'
            },
            {
                type: 'Multiple Login Attempts',
                user: 'jane_smith',
                ip: '192.168.1.101',
                time: new Date('2024-01-17T13:20:00'),
                severity: 'low'
            }
        ];

        container.innerHTML = securityEvents.map(event => `
            <div class="security-log-item ${event.severity}">
                <div class="security-log-icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
                <div class="security-log-details">
                    <h4>${event.type}</h4>
                    <p>User: ${event.user} | IP: ${event.ip}</p>
                    <span class="security-log-time">${event.time.toLocaleString()}</span>
                </div>
                <div class="security-log-actions">
                    <button class="btn btn-sm btn-primary">Investigate</button>
                </div>
            </div>
        `).join('');
    }

    populateUserSelects() {
        const userSelects = document.querySelectorAll('select[id*="User"], select[name*="userId"]');
        userSelects.forEach(select => {
            select.innerHTML = '<option value="">Select User</option>' +
                this.users.map(user =>
                    `<option value="${user.id}">${user.firstName} ${user.lastName} (${user.username})</option>`
                ).join('');
        });
    }

    showSection(sectionName) {
        console.log('Switching to section:', sectionName);

        // Hide all sections
        const sections = document.querySelectorAll('.content-section');
        console.log('Found sections:', sections.length);
        sections.forEach(section => section.classList.remove('active'));

        // Show selected section
        const targetSection = document.getElementById(`${sectionName}-section`);
        console.log('Target section:', targetSection);
        if (targetSection) {
            targetSection.classList.add('active');
            this.updatePageTitle(sectionName);
            console.log('Section activated:', sectionName);
        } else {
            console.error('Section not found:', `${sectionName}-section`);
        }

        // Load section-specific data
        this.loadSectionData(sectionName);
    }

    updatePageTitle(sectionName) {
        const pageTitle = document.getElementById('pageTitle');
        const pageSubtitle = document.getElementById('pageSubtitle');

        const titles = {
            overview: { title: 'Admin Overview', subtitle: 'System administration and monitoring dashboard.' },
            users: { title: 'User Management', subtitle: 'Manage user accounts and permissions.' },
            accounts: { title: 'Account Management', subtitle: 'Monitor and manage all bank accounts.' },
            transactions: { title: 'Transaction Monitor', subtitle: 'Review and approve transactions.' },
            reports: { title: 'Reports & Analytics', subtitle: 'View system reports and analytics.' },
            security: { title: 'Security Center', subtitle: 'Monitor security events and threats.' },
            system: { title: 'System Settings', subtitle: 'Configure system parameters and settings.' },
            audit: { title: 'Audit Logs', subtitle: 'View system audit logs and activities.' }
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

    loadSectionData(sectionName) {
        switch (sectionName) {
            case 'overview':
                this.loadRecentActivity();
                break;
            case 'users':
                this.loadUsers();
                break;
            case 'accounts':
                this.loadAccounts();
                break;
            case 'transactions':
                this.loadTransactions();
                break;
            case 'audit':
                this.loadAuditLogs();
                break;
            case 'security':
                this.loadSecurityLogs();
                break;
        }
    }

    // Filter functions
    filterUsers() {
        const searchTerm = document.getElementById('userSearch').value.toLowerCase();
        const roleFilter = document.getElementById('roleFilter').value;
        const statusFilter = document.getElementById('statusFilter').value;

        const filteredUsers = this.users.filter(user => {
            const matchesSearch = user.username.toLowerCase().includes(searchTerm) ||
                                user.firstName.toLowerCase().includes(searchTerm) ||
                                user.lastName.toLowerCase().includes(searchTerm) ||
                                user.email.toLowerCase().includes(searchTerm);
            const matchesRole = !roleFilter || user.userRole === roleFilter;
            const matchesStatus = !statusFilter || user.status === statusFilter;

            return matchesSearch && matchesRole && matchesStatus;
        });

        this.renderFilteredUsers(filteredUsers);
    }

    filterAccounts() {
        const searchTerm = document.getElementById('accountSearch').value.toLowerCase();
        const typeFilter = document.getElementById('accountTypeFilter').value;
        const statusFilter = document.getElementById('accountStatusFilter').value;

        const filteredAccounts = this.accounts.filter(account => {
            const matchesSearch = account.accountNumber.toLowerCase().includes(searchTerm) ||
                                account.ownerName.toLowerCase().includes(searchTerm);
            const matchesType = !typeFilter || account.accountType === typeFilter;
            const matchesStatus = !statusFilter || account.status === statusFilter;

            return matchesSearch && matchesType && matchesStatus;
        });

        this.renderFilteredAccounts(filteredAccounts);
    }

    filterTransactions() {
        const searchTerm = document.getElementById('transactionSearch').value.toLowerCase();
        const statusFilter = document.getElementById('transactionStatusFilter').value;
        const typeFilter = document.getElementById('transactionTypeFilter').value;
        const dateFilter = document.getElementById('dateFilter').value;

        const filteredTransactions = this.transactions.filter(transaction => {
            const matchesSearch = transaction.fromAccount.toLowerCase().includes(searchTerm) ||
                                transaction.toAccount.toLowerCase().includes(searchTerm) ||
                                transaction.beneficiaryName.toLowerCase().includes(searchTerm);
            const matchesStatus = !statusFilter || transaction.status === statusFilter;
            const matchesType = !typeFilter || transaction.transferType === typeFilter;
            const matchesDate = !dateFilter || transaction.createdAt.toDateString() === new Date(dateFilter).toDateString();

            return matchesSearch && matchesStatus && matchesType && matchesDate;
        });

        this.renderFilteredTransactions(filteredTransactions);
    }

    filterAuditLogs() {
        const searchTerm = document.getElementById('auditSearch').value.toLowerCase();
        const levelFilter = document.getElementById('auditLevelFilter').value;
        const actionFilter = document.getElementById('auditActionFilter').value;
        const dateFilter = document.getElementById('auditDateFilter').value;

        const filteredLogs = this.auditLogs.filter(log => {
            const matchesSearch = log.user.toLowerCase().includes(searchTerm) ||
                                log.details.toLowerCase().includes(searchTerm);
            const matchesLevel = !levelFilter || log.level === levelFilter;
            const matchesAction = !actionFilter || log.action === actionFilter;
            const matchesDate = !dateFilter || log.timestamp.toDateString() === new Date(dateFilter).toDateString();

            return matchesSearch && matchesLevel && matchesAction && matchesDate;
        });

        this.renderFilteredAuditLogs(filteredLogs);
    }

    renderFilteredUsers(users) {
        const container = document.getElementById('usersTableBody');
        if (!container) return;

        container.innerHTML = users.map(user => `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.email}</td>
                <td><span class="role-badge ${user.userRole.toLowerCase()}">${user.userRole}</span></td>
                <td><span class="status-badge ${user.status.toLowerCase()}">${user.status}</span></td>
                <td>${user.lastLogin.toLocaleDateString()}</td>
                <td class="action-buttons">
                    <button class="btn btn-sm btn-primary" onclick="editUser(${user.id})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-warning" onclick="suspendUser(${user.id})">
                        <i class="fas fa-ban"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    }

    renderFilteredAccounts(accounts) {
        const container = document.getElementById('accountsTableBody');
        if (!container) return;

        container.innerHTML = accounts.map(account => `
            <tr>
                <td>${account.id}</td>
                <td>${account.accountNumber}</td>
                <td><span class="type-badge ${account.accountType.toLowerCase()}">${account.accountType}</span></td>
                <td>${account.ownerName}</td>
                <td>$${account.balance.toLocaleString()}</td>
                <td><span class="status-badge ${account.status.toLowerCase()}">${account.status}</span></td>
                <td>${account.createdAt.toLocaleDateString()}</td>
                <td class="action-buttons">
                    <button class="btn btn-sm btn-primary" onclick="viewAccount(${account.id})">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-warning" onclick="suspendAccount(${account.id})">
                        <i class="fas fa-ban"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="closeAccount(${account.id})">
                        <i class="fas fa-times"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    }

    renderFilteredTransactions(transactions) {
        const container = document.getElementById('transactionsTableBody');
        if (!container) return;

        container.innerHTML = transactions.map(transaction => `
            <tr>
                <td>${transaction.id}</td>
                <td>${transaction.fromAccount}</td>
                <td>${transaction.toAccount}</td>
                <td>$${transaction.amount.toFixed(2)}</td>
                <td><span class="type-badge ${transaction.transferType.toLowerCase()}">${transaction.transferType}</span></td>
                <td><span class="status-badge ${transaction.status.toLowerCase()}">${transaction.status}</span></td>
                <td>${transaction.createdAt.toLocaleDateString()}</td>
                <td class="action-buttons">
                    <button class="btn btn-sm btn-primary" onclick="viewTransaction(${transaction.id})">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-success" onclick="approveTransaction(${transaction.id})">
                        <i class="fas fa-check"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="rejectTransaction(${transaction.id})">
                        <i class="fas fa-times"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    }

    renderFilteredAuditLogs(logs) {
        const container = document.getElementById('auditLogsTableBody');
        if (!container) return;

        container.innerHTML = logs.map(log => `
            <tr>
                <td>${log.timestamp.toLocaleString()}</td>
                <td>${log.user}</td>
                <td>${log.action}</td>
                <td><span class="level-badge ${log.level.toLowerCase()}">${log.level}</td>
                <td>${log.details}</td>
                <td>${log.ipAddress}</td>
            </tr>
        `).join('');
    }

    // Modal functions removed - no add user/account functionality

    showModal(modalId) {
        const overlay = document.getElementById('modalOverlay');
        const modal = document.getElementById(modalId);

        if (overlay && modal) {
            overlay.classList.add('active');
            modal.style.display = 'block';

            // Add animation
            if (window.animationManager) {
                window.animationManager.fadeIn(overlay);
                window.animationManager.bounce(modal);
            }
        }
    }

    closeModal() {
        const overlay = document.getElementById('modalOverlay');
        const modals = document.querySelectorAll('.modal');

        if (overlay) {
            overlay.classList.remove('active');

            // Add animation
            if (window.animationManager) {
                window.animationManager.fadeOut(overlay);
            }

            setTimeout(() => {
                modals.forEach(modal => modal.style.display = 'none');
            }, 300);
        }
    }

    // Form handlers removed - no add user/account functionality

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
    console.log('Global showSection called with:', sectionName);
    if (window.adminDashboardManager) {
        window.adminDashboardManager.showSection(sectionName);
    } else {
        console.error('AdminDashboardManager not initialized');
        // Fallback navigation
        const sections = document.querySelectorAll('.content-section');
        sections.forEach(section => section.classList.remove('active'));
        const targetSection = document.getElementById(`${sectionName}-section`);
        if (targetSection) {
            targetSection.classList.add('active');
        }
    }
}

// Add user and account modal functions removed

function closeModal() {
    if (window.adminDashboardManager) {
        window.adminDashboardManager.closeModal();
    }
}

// Admin action functions
function editUser(userId) {
    console.log('Edit user:', userId);
    // Implementation for editing user
}

function suspendUser(userId) {
    console.log('Suspend user:', userId);
    // Implementation for suspending user
}

function deleteUser(userId) {
    console.log('Delete user:', userId);
    // Implementation for deleting user
}

function viewAccount(accountId) {
    console.log('View account:', accountId);
    // Implementation for viewing account
}

function suspendAccount(accountId) {
    console.log('Suspend account:', accountId);
    // Implementation for suspending account
}

function closeAccount(accountId) {
    console.log('Close account:', accountId);
    // Implementation for closing account
}

function viewTransaction(transactionId) {
    console.log('View transaction:', transactionId);
    // Implementation for viewing transaction
}

function approveTransaction(transactionId) {
    console.log('Approve transaction:', transactionId);
    // Implementation for approving transaction
}

function rejectTransaction(transactionId) {
    console.log('Reject transaction:', transactionId);
    // Implementation for rejecting transaction
}

function exportTransactions() {
    console.log('Export transactions');
    // Implementation for exporting transactions
}

function generateReport() {
    console.log('Generate report');
    // Implementation for generating reports
}

function runSecurityScan() {
    console.log('Run security scan');
    // Implementation for security scan
}

function exportAuditLogs() {
    console.log('Export audit logs');
    // Implementation for exporting audit logs
}

function logout() {
    console.log('Logging out...');

    // Add logout animation with multiple effects
    const dashboardContainer = document.querySelector('.dashboard-container');
    const logoutButton = document.querySelector('.sidebar-footer .btn');

    if (logoutButton) {
        // Add click animation to button
        logoutButton.style.transform = 'scale(0.95)';
        logoutButton.style.background = 'linear-gradient(135deg, #ff6b35, #ff8c42)';

        setTimeout(() => {
            logoutButton.style.transform = 'scale(1)';
        }, 150);
    }

    // Add fade out animation to entire dashboard
    if (window.animationManager && dashboardContainer) {
        window.animationManager.fadeOut(dashboardContainer);
    }

    // Add loading spinner during logout
    const loadingOverlay = document.createElement('div');
    loadingOverlay.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
        z-index: 9999;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        opacity: 0;
        transition: opacity 0.5s ease;
    `;

    loadingOverlay.innerHTML = `
        <div style="
            width: 60px;
            height: 60px;
            border: 3px solid #404040;
            border-top: 3px solid #ff6b35;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-bottom: 20px;
        "></div>
        <p style="color: #ff6b35; font-size: 18px; font-weight: 600;">Logging out...</p>
        <style>
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        </style>
    `;

    document.body.appendChild(loadingOverlay);

    // Show loading overlay
    setTimeout(() => {
        loadingOverlay.style.opacity = '1';
    }, 10);

    // Redirect to logout endpoint after animation
    setTimeout(() => {
        window.location.href = '/auth/logout';
    }, 1000);
}

// Initialize admin dashboard manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM Content Loaded');
    // Check if we're on the admin dashboard page
    if (window.location.pathname.includes('admin-dashboard.html') ||
        document.querySelector('.sidebar .logo h2')?.textContent?.includes('Admin')) {
        console.log('Initializing AdminDashboardManager...');
        window.adminDashboardManager = new AdminDashboardManager();
    } else {
        console.log('Not on admin dashboard page');
    }
});

// Fallback initialization if DOMContentLoaded already fired
if (document.readyState === 'loading') {
    // Still loading, wait for DOMContentLoaded
    console.log('Document still loading...');
} else {
    // DOM is already loaded, initialize immediately
    console.log('Document already loaded, checking for admin dashboard...');
    if (window.location.pathname.includes('admin-dashboard.html') ||
        document.querySelector('.sidebar .logo h2')?.textContent?.includes('Admin')) {
        if (!window.adminDashboardManager) {
            console.log('Initializing AdminDashboardManager immediately...');
            window.adminDashboardManager = new AdminDashboardManager();
        }
    }
}

// Force initialization after a short delay as a last resort
setTimeout(() => {
    if (!window.adminDashboardManager &&
        (window.location.pathname.includes('admin-dashboard.html') ||
         document.querySelector('.sidebar .logo h2')?.textContent?.includes('Admin'))) {
        console.log('Force initializing AdminDashboardManager...');
        window.adminDashboardManager = new AdminDashboardManager();
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
