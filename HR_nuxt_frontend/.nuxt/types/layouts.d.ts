import { ComputedRef, MaybeRef } from 'vue'
export type LayoutKey = "blank" | "default"
declare module "../../node_modules/.pnpm/nuxt@3.7.4_@types+node@20.5.9_eslint@8.48.0_sass@1.69.3_stylelint@15.10.3_typescript@5.2.2/node_modules/nuxt/dist/pages/runtime/composables" {
  interface PageMeta {
    layout?: MaybeRef<LayoutKey | false> | ComputedRef<LayoutKey | false>
  }
}