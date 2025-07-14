
import { updateAppConfig } from '#app'
import { defuFn } from 'C:/Users/minsk/Desktop/hr/HR_nuxt_frontend/node_modules/.pnpm/defu@6.1.4/node_modules/defu/dist/defu.mjs'

const inlineConfig = {
  "nuxt": {}
}

// Vite - webpack is handled directly in #app/config
if (import.meta.hot) {
  import.meta.hot.accept((newModule) => {
    updateAppConfig(newModule.default)
  })
}



export default /* #__PURE__ */ defuFn(inlineConfig)
