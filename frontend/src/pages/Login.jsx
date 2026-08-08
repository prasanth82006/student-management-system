import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthService } from '../services/api';

const Login = () => {
    const [credentials, setCredentials] = useState({ email: '', password: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => setCredentials({ ...credentials, [e.target.name]: e.target.value });

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await AuthService.login(credentials, 'STUDENT');
            localStorage.setItem('token', res.data.token);
            localStorage.setItem('role', res.data.role);
            navigate(res.data.role === 'STUDENT' ? '/profile' : '/dashboard');
        } catch (err) {
            setError('Invalid email or password');
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card glass-panel animate-fade-in">
                <h2 style={{ textAlign: 'center', marginBottom: '30px', fontSize: '2rem' }}>Student Login</h2>
                
                {error && <div className="error-banner">{error}</div>}
                
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <label className="input-label">Email Address</label>
                        <input 
                            type="email" 
                            name="email" 
                            className="input-field" 
                            placeholder="student@example.com"
                            onChange={handleChange} 
                            required 
                        />
                    </div>
                    <div className="input-group">
                        <label className="input-label">Password</label>
                        <input 
                            type="password" 
                            name="password" 
                            className="input-field" 
                            placeholder="••••••••"
                            onChange={handleChange} 
                            required 
                        />
                    </div>
                    <button type="submit" className="btn-primary" style={{ marginTop: '10px' }}>
                        Secure Login
                    </button>
                    
                    <p style={{ textAlign: 'center', marginTop: '20px', color: 'var(--text-secondary)' }}>
                        Don't have an account? <a href="/register" style={{ color: 'var(--primary-accent)', textDecoration: 'none' }}>Register here</a>
                    </p>
                    <p style={{ textAlign: 'center', marginTop: '10px', color: 'var(--text-secondary)' }}>
                        Administrator? <a href="/admin" style={{ color: 'var(--primary-accent)', textDecoration: 'none' }}>Admin login</a>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default Login;
