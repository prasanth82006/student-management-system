import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthService } from '../services/api';

export default function AdminLogin() {
    const [credentials, setCredentials] = useState({ email: '', password: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const change = (e) => setCredentials({ ...credentials, [e.target.name]: e.target.value });
    const submit = async (e) => {
        e.preventDefault(); setError('');
        try {
            const res = await AuthService.login(credentials, 'ADMIN');
            localStorage.setItem('token', res.data.token); localStorage.setItem('role', res.data.role);
            navigate('/dashboard');
        } catch (err) { setError(err.response?.data?.message || 'Invalid administrator email or password'); }
    };
    return <div className="auth-container"><div className="auth-card glass-panel animate-fade-in">
        <h2 style={{ textAlign: 'center', marginBottom: '12px', fontSize: '2rem' }}>Administrator Login</h2>
        <p style={{ textAlign: 'center', color: 'var(--text-secondary)', marginBottom: '25px' }}>For administrator accounts only.</p>
        {error && <div className="error-banner">{error}</div>}
        <form onSubmit={submit}>
            <div className="input-group"><label className="input-label">Email Address</label><input type="email" name="email" className="input-field" value={credentials.email} onChange={change} required /></div>
            <div className="input-group"><label className="input-label">Password</label><input type="password" name="password" className="input-field" value={credentials.password} onChange={change} required /></div>
            <button type="submit" className="btn-primary" style={{ marginTop: '10px' }}>Login as Admin</button>
            <p style={{ textAlign: 'center', marginTop: '20px', color: 'var(--text-secondary)' }}>Student? <a href="/login" style={{ color: 'var(--primary-accent)' }}>Student login</a></p>
        </form>
    </div></div>;
}
