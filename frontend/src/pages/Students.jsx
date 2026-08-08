import React, { useState, useEffect } from 'react';
import { StudentService } from '../services/api';
import { Search, Plus, Edit2, Trash2, GraduationCap, X, CheckCircle, AlertCircle, ArrowLeft } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import '../index.css';

const Students = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  
  // Modal State
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const navigate = useNavigate();
  
  // Notification State
  const [notification, setNotification] = useState(null);

  const [formData, setFormData] = useState({
    firstName: '', lastName: '', email: '', phone: '',
    department: '', course: '', year: '', cgpa: ''
  });

  useEffect(() => {
    fetchStudents();
  }, []);

  const showNotification = (message, type = 'success') => {
    setNotification({ message, type });
    setTimeout(() => setNotification(null), 4000);
  };

  const fetchStudents = async () => {
    try {
      setLoading(true);
      // We mapped our Spring API to return a Page object, so the array is in response.data.content
      const response = await StudentService.getAllStudents({ size: 100 }); 
      setStudents(response.data.content || response.data);
    } catch (error) {
      console.error('Error fetching students:', error);
      showNotification('Failed to load students. Is the backend running?', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEditing) {
        await StudentService.updateStudent(formData.id, formData);
        showNotification('Student updated successfully!');
      } else {
        await StudentService.createStudent(formData);
        showNotification('Student added successfully!');
      }
      setIsModalOpen(false);
      fetchStudents();
    } catch (error) {
      const errorMsg = error.response?.data?.details || error.response?.data?.message || 'Operation failed';
      showNotification(errorMsg, 'error');
    }
  };

  const handleEdit = (student) => {
    setFormData(student);
    setIsEditing(true);
    setIsModalOpen(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this student?')) {
      try {
        await StudentService.deleteStudent(id);
        showNotification('Student deleted successfully!');
        fetchStudents();
      } catch (error) {
        showNotification('Failed to delete student.', 'error');
      }
    }
  };

  const openNewModal = () => {
    setFormData({
      firstName: '', lastName: '', email: '', phone: '',
      department: '', course: '', year: '', cgpa: ''
    });
    setIsEditing(false);
    setIsModalOpen(true);
  };

  const filteredStudents = students?.filter(student => 
    student.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    student.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    student.department.toLowerCase().includes(searchTerm.toLowerCase())
  ) || [];

  return (
    <div style={{ padding: '40px 20px', maxWidth: '1200px', margin: '0 auto' }}>
      
      {/* Navbar / Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '40px' }} className="animate-fade-in">
        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
          <button onClick={() => navigate('/dashboard')} className="btn-primary" style={{ width: 'auto', display: 'flex', alignItems: 'center', gap: '8px' }}>
            <ArrowLeft size={20} /> Back to Dashboard
          </button>
          <div style={{ background: 'var(--card-bg)', padding: '12px', borderRadius: '12px' }}>
            <GraduationCap className="text-blue-600" size={32} />
          </div>
          <div>
            <h1 style={{ fontSize: '2rem' }}>Student Management</h1>
            <p style={{ color: 'var(--text-secondary)' }}>Manage student records and information</p>
          </div>
        </div>
        <button className="btn btn-primary" onClick={openNewModal}>
          <Plus size={18} /> Add Student
        </button>
      </div>

      {/* Notification Toast */}
      {notification && (
        <div style={{
          position: 'fixed', top: '20px', right: '20px', zIndex: 1000,
          background: notification.type === 'error' ? 'var(--danger)' : 'var(--success)',
          color: 'white', padding: '12px 20px', borderRadius: 'var(--radius-sm)',
          display: 'flex', alignItems: 'center', gap: '10px', boxShadow: 'var(--shadow-lg)'
        }} className="animate-fade-in">
          {notification.type === 'error' ? <AlertCircle size={20} /> : <CheckCircle size={20} />}
          <span style={{ fontWeight: 500, fontSize: '0.9rem' }}>{notification.message}</span>
        </div>
      )}

      {/* Search and Filters */}
      <div className="glass-panel animate-fade-in" style={{ padding: '20px', marginBottom: '30px', animationDelay: '0.1s' }}>
        <div style={{ position: 'relative', maxWidth: '400px' }}>
          <Search size={18} color="var(--text-secondary)" style={{ position: 'absolute', left: '15px', top: '50%', transform: 'translateY(-50%)' }} />
          <input 
            type="text" 
            className="form-input" 
            placeholder="Search by name or department..." 
            style={{ paddingLeft: '45px' }}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>

      {/* Table */}
      <div className="glass-panel table-container animate-fade-in" style={{ animationDelay: '0.2s' }}>
        {loading ? (
          <div style={{ padding: '40px', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading student data...</div>
        ) : (
          <table className="table">
            <thead>
              <tr>
                <th>Student Name</th>
                <th>Contact Info</th>
                <th>Department</th>
                <th>Enrollment</th>
                <th>CGPA</th>
                <th style={{ textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredStudents.length === 0 ? (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>No students found.</td>
                </tr>
              ) : (
                filteredStudents.map((student) => (
                  <tr key={student.id}>
                    <td>
                      <div style={{ fontWeight: 500 }}>{student.firstName} {student.lastName}</div>
                      <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)', marginTop: '4px' }}>ID: STU-{student.id}</div>
                    </td>
                    <td>
                      <div>{student.email}</div>
                      <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>{student.phone}</div>
                    </td>
                    <td>
                      <span className="badge badge-purple">{student.department}</span>
                      <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginTop: '6px' }}>{student.course}</div>
                    </td>
                    <td><span className="badge badge-blue">Class of {student.year}</span></td>
                    <td>
                      <span className={`badge ${student.cgpa >= 3.5 ? 'badge-green' : 'badge-blue'}`}>
                        {student.cgpa.toFixed(1)}
                      </span>
                    </td>
                    <td style={{ textAlign: 'right' }}>
                      <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                        <button className="btn btn-secondary" style={{ padding: '6px 12px' }} onClick={() => handleEdit(student)}>
                          <Edit2 size={16} />
                        </button>
                        <button className="btn btn-danger" style={{ padding: '6px 12px' }} onClick={() => handleDelete(student.id)}>
                          <Trash2 size={16} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>

      {/* Modal */}
      {isModalOpen && (
        <div style={{
          position: 'fixed', top: 0, left: 0, width: '100%', height: '100%',
          backgroundColor: 'rgba(15, 23, 42, 0.8)', backdropFilter: 'blur(4px)',
          display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 500
        }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '600px', padding: '30px', maxHeight: '90vh', overflowY: 'auto' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '25px' }}>
              <h2>{isEditing ? 'Edit Student Record' : 'Add New Student'}</h2>
              <button style={{ background: 'transparent', border: 'none', color: 'var(--text-secondary)', cursor: 'pointer' }} onClick={() => setIsModalOpen(false)}>
                <X size={24} />
              </button>
            </div>
            
            <form onSubmit={handleSubmit}>
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                <div className="form-group">
                  <label className="form-label">First Name</label>
                  <input type="text" name="firstName" className="form-input" value={formData.firstName} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Last Name</label>
                  <input type="text" name="lastName" className="form-input" value={formData.lastName} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Email Address</label>
                  <input type="email" name="email" className="form-input" value={formData.email} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Phone Number</label>
                  <input type="text" name="phone" className="form-input" value={formData.phone} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Department</label>
                  <input type="text" name="department" className="form-input" value={formData.department} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Course Name</label>
                  <input type="text" name="course" className="form-input" value={formData.course} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Enrollment Year</label>
                  <input type="number" name="year" className="form-input" value={formData.year} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                  <label className="form-label">CGPA</label>
                  <input type="number" step="0.1" name="cgpa" className="form-input" value={formData.cgpa} onChange={handleInputChange} required />
                </div>
              </div>
              
              <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '15px', marginTop: '30px' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setIsModalOpen(false)}>Cancel</button>
                <button type="submit" className="btn btn-primary">
                  {isEditing ? 'Save Changes' : 'Add Student'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

    </div>
  );
}

export default Students;
