import { Button, Dialog, DialogTitle, TextField, Typography } from "@mui/material";


const style = {
    textField:
    {
        width: '80%',
        marginBottom: '10px'
    },

    textDiv:
    {
        display: 'flex',
        justifyContent: 'flex-start',
        flexDirection: 'column',
        alignItems: 'center'

    },
    dialog:
    {
        minHeight: '80vh',
        maxHeight: '80vh',

    }

}

export default function AddAlbum(props) {

    const { onClose } = props


    return (
        <>
            <Dialog open={true} onClose={onClose} fullWidth={true} >

                <DialogTitle titleStyle={{ textAlign: "center" }} >
                    <Typography variant="h5" align="center">Add album</Typography>
                </DialogTitle>

                <div style={{ display: 'flex', justifyContent: 'flex-start', flexDirection: 'column', alignItems: 'center' }}>
                    <TextField id="filled-basic" label="Name" variant="filled" style={{ width: '80%', marginBottom: '10px' }} />
                    <TextField id="filled-basic" label="Description" variant="filled" style={{ width: '80%', marginBottom: '10px' }} />
                </div>
                <Button variant="contained" style={{ backgroundColor: 'black' }}>Submit</Button>


            </Dialog>
        </>)
}