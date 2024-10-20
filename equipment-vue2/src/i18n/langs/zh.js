import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import Common from './common/index'
import Menu from './menu/index'
import Manufacturer from './equip/property/manufacturer/index'
const zh = {
    message: {
        Mark: 'zh',
        Button: Common.Button,
        Menu: Menu.Menu,
        Manufacturer: Manufacturer.Manufacturer,

    },
    ...zhLocale
}
export default zh