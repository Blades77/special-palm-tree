
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name:'datePassed'
})
export class TimeaPassedPipe implements PipeTransform{

    transform(value: Date): String {

   let currentDate = new Date();
    value = new Date(value);
        const mili = currentDate.valueOf() - value.valueOf();
        const days = Math.floor(mili / (24*60*60*1000));
        const hours = Math.floor(mili/(1000*60*60));
     if(hours < 24){
         if(hours < 1){
            const minutes = Math.floor(mili/(1000*60));
                if(minutes < 1){
                    const seconds = Math.floor(mili/1000);
                    return this.compositor(seconds,"second");
                }else{
                    return this.compositor(minutes,"minute");
                }
         }else{
            return this.compositor(hours,"hour");
         }
     }else{
         if(days > 31){
             const months = Math.floor(days/31);
             return this.compositor(months,"month")
         }else{
            return this.compositor(days,"day");
         }

     }

    }

    compositor(value: number,unit: String){
        return (value+" "+unit+this.multiplicity(value)+" ago");
    }
    multiplicity(value: number){
        if(value === 1){
            return("")
        }else{
            return("s");
        }
    }
}