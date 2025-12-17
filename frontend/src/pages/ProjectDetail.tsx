import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { projectsAPI, tasksAPI } from '../api.ts';
import type { ProjectDetail, CreateTaskRequest } from '../types';

const ProjectDetailPage: React.FC = () => {
  const { projectId } = useParams<{ projectId: string }>();
  const navigate = useNavigate();
  const [project, setProject] = useState<ProjectDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [newTask, setNewTask] = useState<CreateTaskRequest>({
    title: '',
    description: '',
    dueDate: '',
  });

  useEffect(() => {
    loadProject();
  }, [projectId]);

  const loadProject = async () => {
    try {
      const data = await projectsAPI.getById(Number(projectId));
      setProject(data);
    } catch (error) {
      console.error('Failed to load project', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTask = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await tasksAPI.create(Number(projectId), {
        ...newTask,
        dueDate: newTask.dueDate || undefined,
      });
      setShowModal(false);
      setNewTask({ title: '', description: '', dueDate: '' });
      loadProject();
    } catch (error) {
      console.error('Failed to create task', error);
      alert('Failed to create task');
    }
  };

  const handleMarkComplete = async (taskId: number) => {
    try {
      await tasksAPI.markComplete(Number(projectId), taskId);
      loadProject();
    } catch (error) {
      console.error('Failed to mark task as complete', error);
      alert('Failed to mark task as complete');
    }
  };

  const handleDeleteTask = async (taskId: number) => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      try {
        await tasksAPI.delete(Number(projectId), taskId);
        loadProject();
      } catch (error) {
        console.error('Failed to delete task', error);
        alert('Failed to delete task');
      }
    }
  };

  if (loading) {
    return (
        <div className="min-h-screen flex items-center justify-center">
          <div className="text-xl text-gray-600">Loading...</div>
        </div>
    );
  }

  if (!project) {
    return (
        <div className="min-h-screen flex items-center justify-center">
          <div className="text-xl text-gray-600">Project not found</div>
        </div>
    );
  }

  return (
      <div className="min-h-screen bg-gray-50">
        {/* Header */}
        <header className="bg-white shadow-sm">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
            <button
                onClick={() => navigate('/')}
                className="text-blue-600 hover:text-blue-700 mb-2 flex items-center gap-2"
            >
              ← Back to Projects
            </button>
            <h1 className="text-3xl font-bold text-gray-900">{project.title}</h1>
            <p className="text-gray-600 mt-1">{project.description || 'No description'}</p>
          </div>
        </header>

        {/* Progress Section */}
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div className="bg-white rounded-xl shadow-sm p-6">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold text-gray-900">Project Progress</h3>
              <span className="text-2xl font-bold text-blue-600">
              {project.progressPercentage.toFixed(0)}%
            </span>
            </div>
            <div className="w-full bg-gray-200 rounded-full h-4 mb-2">
              <div
                  className="bg-blue-600 h-4 rounded-full transition-all"
                  style={{ width: `${project.progressPercentage}%` }}
              ></div>
            </div>
            <p className="text-sm text-gray-600">
              {project.completedTasks} of {project.totalTasks} tasks completed
            </p>
          </div>
        </div>

        {/* Tasks Section */}
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div className="mb-6 flex justify-between items-center">
            <h2 className="text-xl font-semibold text-gray-800">
              Tasks ({project.tasks.length})
            </h2>
            <button
                onClick={() => setShowModal(true)}
                className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition font-medium"
            >
              + Add Task
            </button>
          </div>

          {project.tasks.length === 0 ? (
              <div className="text-center py-12 bg-white rounded-xl">
                <p className="text-gray-500 text-lg mb-4">No tasks yet</p>
                <button
                    onClick={() => setShowModal(true)}
                    className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
                >
                  Create Your First Task
                </button>
              </div>
          ) : (
              <div className="space-y-3">
                {project.tasks.map((task) => (
                    <div
                        key={task.id}
                        className={`bg-white rounded-lg shadow-sm p-4 border transition ${
                            task.isCompleted
                                ? 'border-green-200 bg-green-50'
                                : 'border-gray-200'
                        }`}
                    >
                      <div className="flex items-start gap-4">
                        {/* Checkbox - disabled if already completed */}
                        <div className="mt-1">
                          {task.isCompleted ? (
                              <div className="h-5 w-5 bg-green-600 rounded flex items-center justify-center">
                                <svg className="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                                </svg>
                              </div>
                          ) : (
                              <input
                                  type="checkbox"
                                  checked={false}
                                  onChange={() => handleMarkComplete(task.id)}
                                  className="h-5 w-5 text-blue-600 rounded focus:ring-2 focus:ring-blue-500 cursor-pointer"
                              />
                          )}
                        </div>

                        <div className="flex-1">
                          <div className="flex items-center gap-2">
                            <h3
                                className={`text-lg font-semibold ${
                                    task.isCompleted
                                        ? 'line-through text-gray-500'
                                        : 'text-gray-900'
                                }`}
                            >
                              {task.title}
                            </h3>
                            {task.isCompleted && (
                                <span className="px-2 py-0.5 text-xs font-medium text-green-800 bg-green-100 rounded-full">
                          ✓ Completed
                        </span>
                            )}
                          </div>
                          {task.description && (
                              <p className={`text-sm mt-1 ${
                                  task.isCompleted ? 'text-gray-500' : 'text-gray-600'
                              }`}>
                                {task.description}
                              </p>
                          )}
                          <div className="flex gap-4 mt-2 text-xs text-gray-500">
                            {task.dueDate && (
                                <span>📅 Due: {new Date(task.dueDate).toLocaleDateString()}</span>
                            )}
                            <span>
                        Created: {new Date(task.createdAt).toLocaleDateString()}
                      </span>
                          </div>
                        </div>

                        <button
                            onClick={() => handleDeleteTask(task.id)}
                            className="text-red-600 hover:text-red-700 px-3 py-1 rounded hover:bg-red-50 transition font-medium"
                        >
                          🗑 Delete
                        </button>
                      </div>
                    </div>
                ))}
              </div>
          )}
        </main>

        {/* Create Task Modal */}
        {showModal && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
              <div className="bg-white rounded-2xl shadow-2xl max-w-md w-full p-6">
                <h2 className="text-2xl font-bold text-gray-900 mb-4">Add New Task</h2>
                <form onSubmit={handleCreateTask} className="space-y-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Task Title *
                    </label>
                    <input
                        type="text"
                        value={newTask.title}
                        onChange={(e) =>
                            setNewTask({ ...newTask, title: e.target.value })
                        }
                        required
                        className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                        placeholder="Enter task title"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Description
                    </label>
                    <textarea
                        value={newTask.description}
                        onChange={(e) =>
                            setNewTask({ ...newTask, description: e.target.value })
                        }
                        rows={3}
                        className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                        placeholder="Enter task description (optional)"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Due Date
                    </label>
                    <input
                        type="date"
                        value={newTask.dueDate}
                        onChange={(e) =>
                            setNewTask({ ...newTask, dueDate: e.target.value })
                        }
                        className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                    />
                  </div>

                  <div className="flex gap-3 pt-4">
                    <button
                        type="button"
                        onClick={() => {
                          setShowModal(false);
                          setNewTask({ title: '', description: '', dueDate: '' });
                        }}
                        className="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition"
                    >
                      Cancel
                    </button>
                    <button
                        type="submit"
                        className="flex-1 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition font-medium"
                    >
                      Add Task
                    </button>
                  </div>
                </form>
              </div>
            </div>
        )}
      </div>
  );
};

export default ProjectDetailPage;