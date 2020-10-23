import React, {Component} from 'react';
import '../styles/Order.css';

const URL = 'http://localhost:8080/orders';

class Order extends Component {

    status = {
        deleteButtonDisabled: false
    };

    constructor(props) {
        super(props);
        this.state = {
            data: {},
        };
    }

    componentDidMount() {
        fetch(URL).then((response) => response.json())
            .then((result) => {
                this.setState({
                    data: result
                });
            });
    }

    handler(id) {
        this.status.deleteButtonDisabled = true;
        fetch('http://localhost:8080/order/' + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(result => {
                alert("删除成功");
                this.status.deleteButtonDisabled = false;
            })
            .catch(result => {
                alert("订单删除失败，请稍后再试");
                console.log(result);
                this.status.deleteButtonDisabled = false;
            })
    }

    render() {
        return (
            <div className="order">
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <h3>名字</h3>
                        </td>
                        <td>
                            <h3>单价</h3>
                        </td>
                        <td>
                            <h3>数量</h3>
                        </td>
                        <td>
                            <h3>单位</h3>
                        </td>
                        <td>
                            <h3>操作</h3>
                        </td>
                    </tr>
                    {Object.keys(this.state.data)
                        .map((key) => (
                            <tr key={key}>
                                <td><p>{this.state.data[key].name}</p></td>
                                <td><p>{this.state.data[key].price}</p></td>
                                <td><p>{this.state.data[key].number}</p></td>
                                <td><p>{this.state.data[key].unit}</p></td>
                                <td>
                                    <button
                                        className="btn btn-success m-2"
                                        onClick={() => this.handler(this.state.data[key].id)}
                                        disabled={this.status.deleteButtonDisabled ? 'disabled' : ''}
                                    >删除
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Order;