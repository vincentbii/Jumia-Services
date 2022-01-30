'use strict';

import {
    FormControl,
    makeStyles,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    withStyles
} from "@material-ui/core";

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import CssBaseline from '@material-ui/core/CssBaseline';
import Container from '@material-ui/core/Container';
import * as PropTypes from "prop-types";
import {Component} from "react";
import {MenuItem, Select} from "@mui/material";
import {Label} from "@material-ui/icons";

const follow = require('./follow'); // function to hop multiple links by "rel"

const root = '/api/customers';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            customers: [],
            attributes: [],
            pageSize: 25,
            pageNo: 0,
            pageCount: 0,
            country: '',
            countries: ['Cameroon', 'Uganda', 'Ethiopia', 'Mozambique', 'Morocco'],
            states: ['valid', 'invalid'],
            state: '',
            links: {}
        };
        this.onNavigate = this.onNavigate.bind(this);
    }

    loadFromServer(pageSize, pageNo, country = '', state = '') {

        follow(client, root, {
                pageSize: pageSize,
                pageNo: pageNo,
                country: country,
                state: state
            }
        )
            .done(customers => {
                this.setState({
                    customers: customers,
                    pageSize: pageSize,
                    pageNo: pageNo,
                    pageCount: customers.length,
                    country: country,
                    state: state,
                    links: this.links
                });
            });
    }


    onNavigate(size, page, country = '', state = '') {
        this.loadFromServer(size, page, country, state)
    }


    componentDidMount() {
        this.loadFromServer(this.state.pageSize, this.state.pageNo, this.state.country, this.props.state);
    }

    render() {
        return (
            <div>
                <CustomerList customers={this.state.customers}
                              links={this.state.links}
                              pageSize={this.state.pageSize}
                              pageNo={this.state.pageNo}
                              pageCount={this.state.pageCount}
                              countries={this.state.countries}
                              states={this.state.states}
                              attributes={this.state.attributes}
                              onNavigate={this.onNavigate}
                              updatePageSize={this.updatePageSize}/>
            </div>
        )
    }
}

// const useStyles = makeStyles({
//     table: {
//         minWidth: 700,
//     },
// });

const classes = makeStyles({
    table: {
        minWidth: 700,
    },
});

const app_classes = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
}));

const StyledTableCell = withStyles((theme) => ({
    head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);


class CustomerList extends React.Component {

    constructor(props) {
        super(props);
        this.handleNavFirst = this.handleNavFirst.bind(this);
        this.handleNavPrev = this.handleNavPrev.bind(this);
        this.handleNavNext = this.handleNavNext.bind(this);
        this.handleNavLast = this.handleNavLast.bind(this);
        this.handleInput = this.handleInput.bind(this);
    }

    handleInput(e) {
        e.preventDefault();
        const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
        if (/^[0-9]+$/.test(pageSize)) {
            this.props.updatePageSize(pageSize);
        } else {
            ReactDOM.findDOMNode(this.refs.pageSize).value = pageSize.substring(0, pageSize.length - 1);
        }
    }

    handleNavFirst(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.pageSize, 0);
    }

    handleNavPrev(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.pageSize, this.props.pageNo - 1);
    }

    handleNavNext(e) {
        e.preventDefault();
        let pageNo = this.props.pageNo + 1;
        this.props.onNavigate(this.props.pageSize, pageNo);
    }

    handleNavLast(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.pageSize, this.props.links.last.href);
    }

    setCountry(country) {
        this.props.onNavigate(this.props.pageSize, this.props.pageNo, country, this.props.state);
    }

    setPhoneState(state) {
        this.props.onNavigate(this.props.pageSize, this.props.pageNo, this.props.country, state);
    }

    render() {
        const customers = this.props.customers.map(customer => {
                return <Customer key={customer.id}
                                 customer={customer}
                                 attributes={this.props.attributes}
                />
            }
        );

        const countries = this.props.countries.map(country => {
            return <CountryOption key={country}
                                  country={country}
            />
        })

        const states = this.props.states.map(state => {
            return <StateOption key={state}
                                state={state}
            />
        })


        const navLinks = [];
        if (this.props.pageNo > 0) {
            navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
            navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
        }
        if (this.props.pageSize === this.props.pageCount)
            navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);

        return (

            <React.Fragment>
                <CssBaseline/>
                <Container fixed>
                    <AppBarHeader />
                    <Typography component="div">
                        <TableContainer component={Paper}>
                            <Table className={classes.table} size="small" aria-label="a dense table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell>#</StyledTableCell>
                                        <StyledTableCell align="right">Name</StyledTableCell>
                                        <StyledTableCell align="right">Phone</StyledTableCell>
                                        <StyledTableCell align="right">Country</StyledTableCell>
                                        <StyledTableCell align="right">Country Code</StyledTableCell>
                                        <StyledTableCell align="right">State</StyledTableCell>
                                    </TableRow>
                                    <TableRow>
                                        <TableCell/>
                                        <TableCell align="right"/>
                                        <TableCell align="right"/>
                                        <TableCell align="right">
                                            <select onChange={(event => {
                                                this.setCountry(event.target.value)
                                            })}>
                                                <option value=""> -- all Countries --</option>
                                                {countries}
                                            </select>
                                        </TableCell>
                                        <TableCell align="right"/>
                                        <TableCell align="right">
                                            <select onChange={(event => {
                                                this.setPhoneState(event.target.value)
                                            })}>
                                                <option value=""> -- all States --</option>
                                                {states}
                                            </select>
                                        </TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {customers}
                                </TableBody>
                                <TableHead>
                                    <TableCell align="right" rowSpan={6}>
                                        {navLinks}
                                    </TableCell>
                                </TableHead>
                            </Table>
                        </TableContainer>

                    </Typography>
                </Container>
            </React.Fragment>

        )
    }

}

class AppBarHeader extends React.Component {
    render() {
        return (
            <AppBar position="static">
                <Toolbar variant="dense">
                    <IconButton edge="start" className={app_classes.menuButton} color="inherit" aria-label="menu">
                    </IconButton>
                    <Typography variant="h6" color="inherit">
                        Jumia | Pay
                    </Typography>
                </Toolbar>
            </AppBar>
        )
    }

}

const StyledTableRow = withStyles((theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);

// StyledTableRow.propTypes = {children: PropTypes.node};

class Customer extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (

            <StyledTableRow key={this.props.customer.id}>
                <StyledTableCell component="th">
                    {this.props.customer.id}
                </StyledTableCell>
                <StyledTableCell align="right">{this.props.customer.name}</StyledTableCell>
                <StyledTableCell align="right">{this.props.customer.phone}</StyledTableCell>
                <StyledTableCell align="right">{this.props.customer.country}</StyledTableCell>
                <StyledTableCell align="right">{this.props.customer.countryCode}</StyledTableCell>
                <StyledTableCell align="right">{this.props.customer.state}</StyledTableCell>
            </StyledTableRow>
            // <tr>
            //     <td>{this.props.customer.id}</td>
            //     <td>{this.props.customer.name}</td>
            //     <td>{this.props.customer.phone}</td>
            //     <td>{this.props.customer.country}</td>
            //     <td>{this.props.customer.countryCode}</td>
            //     <td>{this.props.customer.state}</td>
            // </tr>
        )
    }
}

class CountryOption extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <option value={this.props.country}>{this.props.country}</option>
        )
    }
}

class StateOption extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <option value={this.props.state}>{this.props.state}</option>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
)