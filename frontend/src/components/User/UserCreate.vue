<template>
  <div>
    <h2>User Create</h2>
    <div id="form">
      <div class="form-row">
        <div class="col">
          <input
            id="firtname"
            type="text"
            class="form-control"
            :class="{'error-input': !$v.user.firstname.required || !$v.user.firstname.minLength}"
            placeholder="First name"
            v-model.trim="$v.user.firstname.$model"
          >
          <p class="error" v-if="!$v.user.firstname.required">Firstname is required.</p>
          <p
            class="error"
            v-if="!$v.user.firstname.minLength"
          >Firstname must have at least {{$v.user.firstname.$params.minLength.min}} letters.</p>
        </div>
        <div class="col">
          <input
            id="lastname"
            type="text"
            class="form-control"
            :class="{'error-input': !$v.user.lastname.required || !$v.user.lastname.minLength}"
            placeholder="Last name"
            v-model.trim="$v.user.lastname.$model"
          >
          <p class="error" v-if="!$v.user.lastname.required">Lastname is required.</p>
          <p
            class="error"
            v-if="!$v.user.lastname.minLength"
          >Lastname must have at least {{$v.user.lastname.$params.minLength.min}} letters.</p>
        </div>
        <div class="col">
          <input
            id="age"
            type="number"
            class="form-control"
            :class="{'error-input': !$v.user.age.required || !$v.user.age.minValue}"
            placeholder="Age"
            v-model.trim="$v.user.age.$model"
          >
          <p class="error" v-if="!$v.user.age.required">Age is required.</p>
          <p class="error" v-if="!$v.user.age.minValue">Age must be at least {{ $v.user.age.$params.minValue.min }} value.</p>
        </div>
      </div>
      <br>
      <button class="btn btn-primary" @click="createUser">Create</button>
    </div>
  </div>
</template>

<script>
import { EventBus } from "./../../event-bus";
import { required, minLength, minValue } from "vuelidate/lib/validators";

export default {
  name: "UserCreate",
  data() {
    return {
      user: {
        id: this.$route.params.id,
        firstname: null,
        lastname: null,
        age: 0
      }
    };
  },
  validations: {
    user: {
      firstname: {
        required,
        minLength: minLength(3)
      },
      lastname: {
        required,
        minLength: minLength(3)
      },
      age: {
        required,
        minValue: minValue(0)
      }
    }
  },
  methods: {
    resetInputs() {
      this.user.firstname = null;
      this.user.lastname = null;
      this.user.age = 0;
    },
    createUser() {
      this.$http.post("save", this.user).then(
        response => {
          EventBus.$emit("userIsCreated", response.body);
        },
        error => {
          EventBus.$emit("userIsCreated", error.body.errorCode);
        }
      );
      this.resetInputs();
    }
  }
};
</script>
