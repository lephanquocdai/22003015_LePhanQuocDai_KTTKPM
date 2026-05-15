import { useState, useEffect } from 'react'
import axios from 'axios'
import './index.css'

const API_GATEWAY = 'http://localhost:8080'

function App() {
  const [user, setUser] = useState(null)
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [foods, setFoods] = useState([])
  const [cart, setCart] = useState([])
  const [orders, setOrders] = useState([])
  const [view, setView] = useState('foods')

  useEffect(() => {
    if (user) {
      fetchFoods()
      fetchOrders()
    }
  }, [user])

  const login = async () => {
    try {
      await axios.post(`${API_GATEWAY}/users/login`, { username, password })
      setUser({ id: 1, username }) 
      alert('Logged in!')
    } catch (e) {
      alert('Login failed. Ensure backend services are running.')
    }
  }

  const register = async () => {
    try {
      await axios.post(`${API_GATEWAY}/users/register`, { username, password, role: 'USER' })
      alert('Registered successfully!')
    } catch (e) {
      alert('Registration failed. Ensure backend services are running.')
    }
  }

  const fetchFoods = async () => {
    try {
      const res = await axios.get(`${API_GATEWAY}/foods`)
      setFoods(res.data)
    } catch (e) {
      console.error(e)
    }
  }

  const fetchOrders = async () => {
    try {
      const res = await axios.get(`${API_GATEWAY}/orders`)
      setOrders(res.data)
    } catch (e) {
      console.error(e)
    }
  }

  const addToCart = (food) => {
    const existing = cart.find(item => item.id === food.id)
    if (existing) {
      setCart(cart.map(item => item.id === food.id ? { ...item, quantity: item.quantity + 1 } : item))
    } else {
      setCart([...cart, { ...food, quantity: 1 }])
    }
  }

  const placeOrder = async () => {
    for (let item of cart) {
      for (let i = 0; i < item.quantity; i++) {
        try {
          const order = { userId: user.id || 1, foodId: item.id }
          const res = await axios.post(`${API_GATEWAY}/orders`, order)
          await axios.post(`${API_GATEWAY}/payments`, { orderId: res.data.id, amount: item.price, method: 'Banking' })
        } catch (e) {
          alert(`Failed to place order for ${item.name}`)
        }
      }
      alert(`Order placed & paid for ${item.quantity}x ${item.name}!`)
    }
    setCart([])
    fetchOrders()
  }

  if (!user) {
    return (
      <div className="container" style={{ maxWidth: '400px', marginTop: '100px' }}>
        <div className="card">
          <h2 style={{ textAlign: 'center', marginBottom: '30px' }}>Mini Food Ordering</h2>
          <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
          <input placeholder="Password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
          <div style={{ display: 'flex', gap: '10px', marginTop: '20px' }}>
            <button onClick={login} style={{ flex: 1 }}>Login</button>
            <button onClick={register} style={{ flex: 1, background: 'transparent', border: '1px solid var(--primary-color)' }}>Register</button>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Mini Food Ordering System</h1>
        <div>Welcome, <strong style={{color: 'var(--primary-color)'}}>{user.username}</strong></div>
      </div>

      <div className="nav-bar">
        <button onClick={() => setView('foods')} style={{ background: view === 'foods' ? '' : 'transparent' }}>Menu</button>
        <button onClick={() => setView('cart')} style={{ background: view === 'cart' ? '' : 'transparent' }}>Cart ({cart.reduce((a, b) => a + b.quantity, 0)})</button>
        <button onClick={() => setView('orders')} style={{ background: view === 'orders' ? '' : 'transparent' }}>My Orders</button>
      </div>

      {view === 'foods' && (
        <div>
          <h3 style={{marginBottom: '20px'}}>Delicious Foods</h3>
          <div className="food-grid">
            {foods.length > 0 ? foods.map(f => (
              <div key={f.id} className="card">
                <div style={{ height: '120px', background: 'rgba(255,255,255,0.1)', borderRadius: '8px', marginBottom: '15px' }}></div>
                <h4>{f.name}</h4>
                <p style={{ fontSize: '1.2rem', color: 'var(--secondary-color)' }}>${f.price}</p>
                <button onClick={() => addToCart(f)} style={{ width: '100%', marginTop: '10px' }}>Add to Cart</button>
              </div>
            )) : <p>No foods available. Did you start the Food Service?</p>}
          </div>
        </div>
      )}

      {view === 'cart' && (
        <div className="card">
          <h3>Your Cart</h3>
          {cart.length === 0 ? <p>Your cart is empty.</p> : (
            <div>
              <ul style={{ listStyle: 'none', padding: 0 }}>
                {cart.map((c, i) => (
                  <li key={i} style={{ padding: '10px 0', borderBottom: '1px solid rgba(255,255,255,0.1)', display: 'flex', justifyContent: 'space-between' }}>
                    <span>{c.name} <strong style={{color: 'var(--primary-color)'}}>x{c.quantity}</strong></span>
                    <span>${c.price * c.quantity}</span>
                  </li>
                ))}
              </ul>
              <div style={{ textAlign: 'right', marginTop: '20px' }}>
                <h3 style={{ display: 'inline-block', marginRight: '20px' }}>Total: ${cart.reduce((a, b) => a + (b.price * b.quantity), 0)}</h3>
                <button onClick={placeOrder}>Place Order & Pay</button>
              </div>
            </div>
          )}
        </div>
      )}

      {view === 'orders' && (
        <div className="card">
          <h3>My Orders</h3>
          {orders.length === 0 ? <p>No orders placed yet.</p> : (
            <ul style={{ listStyle: 'none', padding: 0 }}>
              {orders.map(o => (
                <li key={o.id} style={{ padding: '15px', marginBottom: '10px', background: 'rgba(255,255,255,0.05)', borderRadius: '8px' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <strong>Order #{o.id}</strong>
                    <span style={{ color: o.status.includes('PAID') ? 'var(--secondary-color)' : 'var(--primary-color)' }}>{o.status}</span>
                  </div>
                  <div style={{ marginTop: '5px', fontSize: '0.9rem', opacity: 0.8 }}>Food ID: {o.foodId}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  )
}

export default App
