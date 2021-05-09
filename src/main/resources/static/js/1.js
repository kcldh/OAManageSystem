// let person = {name:"徐伟鹏", pass:"xwp123"};
//
// let state = new Proxy(person, {
//     get(obj, key){
//         console.log(obj, key);
//         return obj;
//     },
//     set(obj, key, value) {
//         console.log(obj, key, value);
//         obj.key = value;
//     }
// });
//
// state.name = "徐伟鹏1";
// console.log(state.person);


let arr = [1,2,3];
let state = new Proxy(arr, {
    get(obj, key){
        // console.log(obj, key);
        return obj[key];
    },
    set(obj, key, value){

        obj[key] = value;

        return true;
    }
})
state[0] = 100;
state.push(100);
console.log(state)