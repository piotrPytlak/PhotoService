import { Avatar, LinearProgress, List, ListItem, ListItemText } from "@mui/material"
import { useCallback, useContext, useEffect, useState } from "react"
import { apiContext } from "../../store/ApiContext"
import { Scrollbars } from "react-custom-scrollbars"
import { makeStyles } from "@mui/styles";
import GroupIcon from '@mui/icons-material/Group';
import { Redirect } from "react-router-dom";

const style = {
    listItem: {
        borderBottom: '1px solid #9D9C9CFF',
        // borderTop: '1px solid #9D9C9CFF'
    },


    listElement: {
        width: '60px',
        height: '60px',
        boxShadow: '-2px 3px 6px black',
        border: '1px solid white',
        marginRight: '20px'
    },
    icon: {
        marginRight: '8px'
    }
}

const styles = makeStyles((theme) => ({
    listItemText: {
        fontSize: '22px',//Insert your required size
        color: 'black'
    },

    listItemTextSec: {
        display: 'flex',
        alignItems: 'center'

    }


}))


export default function SearchBar(props) {

    const { serverUrl, searchBar } = useContext(apiContext)
    const [user, setUser] = useState([])
    const [load, setLoad] = useState(false)
    const [selectedUser, setSelectedUser] = useState()
    const { param } = props
    const classes = styles()


    const redirectUserPage = useCallback((user) => {
        setSelectedUser(user)
    }, [setSelectedUser])


    useEffect(() => {
        setLoad(true)
        searchBar(param)
            .then(x => setUser(x))
            .finally(setLoad(false))

    }, [param, searchBar]);


    return (
        <>
            <Scrollbars autoHeight autoHeightMax={'40vh'}>
                <List>
                    {user.map((x, index) =>
                    (
                        <ListItem button onClick={redirectUserPage.bind(this, x)} key={index + 'listItem'}
                            style={style.listItem}>
                            <Avatar sx={style.listElement}
                                key={index + 'avatar'}
                                src={serverUrl + "images/" + x.avatarImgPath} />
                            <ListItemText
                                classes={{ primary: classes.listItemText, secondary: classes.listItemTextSec }}
                                primary={x.username}
                                secondary={(
                                    <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                                        <GroupIcon style={style.icon} />
                                        <span>{x.followers + ' followers'}</span>
                                    </div>
                                )} />
                        </ListItem>
                    )
                    )
                    }
                </List>
                {load && <LinearProgress />}
            </Scrollbars>
            {
                !!selectedUser && < Redirect to={`/user/${selectedUser.userId}`} />
            }
        </>
    )

}