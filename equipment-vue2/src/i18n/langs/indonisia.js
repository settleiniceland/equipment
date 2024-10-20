import idLocale from 'element-ui/lib/locale/lang/id'
import Common from './common/index'
import Menu from './menu/index'
import Manufacturer from './equip/property/manufacturer/index'
const id = {
    message: {
        Mark: 'id',
        Button: Common.ButtonId,
        Menu: Menu.MenuId,
        Manufacturer: Manufacturer.ManufacturerId,

    },
    ...idLocale
}
export default id